package com.hengaiw.pay.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hengaiw.model.dao.model.PayChannel;
import com.hengaiw.model.dao.model.PayOrder;
import com.hengaiw.model.service.HaPayOrderService;
import com.hengaiw.model.service.PayChannelService;
import com.hengaiw.pay.wx.config.HaWxConfigImpl;
import com.hengaiw.pay.wx.sdk.WXPay;
import com.hengaiw.pay.wx.sdk.WXPayConstants;
import com.hengaiw.pay.wx.sdk.WXPayUtil;
import com.hengaiw.pub.constant.PayConstants;
import com.hengaiw.pub.utils.HaLog;
/**
 * @Description: 接收处理微信通知
 
 */
@RestController
public class PayWxNotifyController extends PayNotifyBase {

	private static final HaLog _log = HaLog.getLog(PayWxNotifyController.class);

	private WXPay wxpay;
	
	@Autowired
	private HaPayOrderService payOrderService;

	@Autowired
	private PayChannelService payChannelService;

	@Value("${wx.cert.root.path}")
	private String certRootPath;
	/**
	 * 微信支付(统一下单接口)后台通知响应
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
     */
	@RequestMapping("/pay/wxPayNotifyRes.htm")
	@ResponseBody
	public String wxPayNotifyRes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//return certRootPath;
		return doWxPayRes(request, response);
	}

	public String doWxPayRes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logPrefix = "【微信支付回调通知】";
		_log.info("====== 开始接收微信支付回调通知 ======");
		try {
			String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
			//将结果转换成Map
			Map<String,String> result=WXPayUtil.xmlToMap(xmlResult);
			Map<String, Object> payContext = new HashMap();
			payContext.put("parameters", result);
			// 验证业务数据是否正确,验证通过后返回PayOrder和WxPayConfig对象
			if(!verifyWxPayParams(payContext)) {
				return WXPayUtil.getFailReturnXml((String)payContext.get("retMsg"));
			}
			PayOrder payOrder = (PayOrder) payContext.get("payOrder");
			HaWxConfigImpl config=(HaWxConfigImpl) payContext.get("wxPayConfig");
			wxpay = new WXPay(config);
			// 这里做了签名校验(这里又做了一次xml转换对象,可以考虑优化)
			wxpay.isPayResultNotifySignatureValid(result);
			// 处理订单
			byte payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
			if (payStatus != PayConstants.PAY_STATUS_SUCCESS && payStatus != PayConstants.PAY_STATUS_COMPLETE) {
				int updatePayOrderRows = payOrderService.updateStatus4Success(payOrder.getPayOrderId());
				if (updatePayOrderRows != 1) {
					_log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder.getPayOrderId(), PayConstants.PAY_STATUS_SUCCESS);
					return WXPayUtil.getFailReturnXml("处理订单失败");
				}
				_log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstants.PAY_STATUS_SUCCESS);
				payOrder.setStatus(PayConstants.PAY_STATUS_SUCCESS);
			}
			// 业务系统后端通知
			doNotify(payOrder);
			_log.info("====== 完成接收微信支付回调通知 ======");
			return WXPayUtil.getSuccessReturnXml("处理订单成功");
		} catch (Exception e) {
			_log.error(e, "微信回调结果异常,异常原因");
			return WXPayUtil.getFailReturnXml("处理订单失败");
		}
	}
	
	/**
	 * 验证微信支付通知参数
	 * @return
	 */
	public boolean verifyWxPayParams(Map<String, Object> payContext) {
		Map<String,String> params = (Map<String, String>)payContext.get("parameters");

		//校验结果是否成功
		if (!PayConstants.RETURN_VALUE_SUCCESS.equalsIgnoreCase(params.get("result_code"))
				|| !PayConstants.RETURN_VALUE_SUCCESS.equalsIgnoreCase(params.get("result_code"))) {
			_log.error("returnCode={},resultCode={},errCode={},errCodeDes={}", params.get("return_code"), params.get("result_code"), params.get("err_code"), params.get("err_code_des"));
			payContext.put("retMsg", "notify data failed");
			return false;
		}

		Integer total_fee =Integer.parseInt(params.get("total_fee"));   			// 总金额
		String out_trade_no = params.get("out_trade_no");			// 商户系统订单号

		// 查询payOrder记录
		String payOrderId = out_trade_no;
		PayOrder payOrder = payOrderService.selectPayOrder(payOrderId);
		if (payOrder==null) {
			_log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
			payContext.put("retMsg", "Can't found payOrder");
			return false;
		}

		// 查询payChannel记录
		String mchId = payOrder.getMchId();
		String channelId = payOrder.getChannelId();
		PayChannel payChannel = payChannelService.selectPayChannel(channelId, mchId);
		if(payChannel == null) {
			_log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId, mchId, channelId);
			payContext.put("retMsg", "Can't found payChannel");
			return false;
		}
		HaWxConfigImpl config = null;
		try {
			config = HaWxConfigImpl.getInstance(payChannel.getParam(), certRootPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		payContext.put("wxPayConfig", config);

		// 核对金额
		long wxPayAmt = new BigDecimal(total_fee).longValue();
		long dbPayAmt = payOrder.getAmount().longValue();
		if (dbPayAmt != wxPayAmt) {
			_log.error("db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}", total_fee, payOrderId);
			payContext.put("retMsg", "total_fee is not the same");
			return false;
		}

		payContext.put("payOrder", payOrder);
		return true;
	}

}
