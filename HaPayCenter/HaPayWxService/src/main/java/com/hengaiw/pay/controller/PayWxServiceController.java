package com.hengaiw.pay.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.model.dao.model.MchInfo;
import com.hengaiw.model.dao.model.PayChannel;
import com.hengaiw.model.dao.model.PayOrder;
import com.hengaiw.model.service.MchInfoService;
import com.hengaiw.model.service.PayChannelService;
import com.hengaiw.model.service.HaPayOrderService;
import com.hengaiw.pay.wx.config.HaWxConfigImpl;
import com.hengaiw.pay.wx.sdk.WXPay;
import com.hengaiw.pay.wx.sdk.WXPayConstants;
import com.hengaiw.pay.wx.sdk.WXPayConstants.SignType;
import com.hengaiw.pay.wx.sdk.WXPayUtil;
import com.hengaiw.pub.constant.PayConstants;
import com.hengaiw.pub.constant.PayEnum;
import com.hengaiw.pub.utils.HaBase64;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;

/**
 * 微信支付相关接口
 * 
 * @author jianhuizhang
 *
 */
@RestController
@RequestMapping(value = "/pay/wx")
public class PayWxServiceController {

	private final HaLog _log = HaLog.getLog(PayWxServiceController.class);
	
	@Value("${wx.cert.root.path}")
	private String certRootPath;

	@Value("${wx.notify_url}")
	private String notify_url;

	private WXPay wxpay;

	@Autowired
	private MchInfoService mchInfoService;
	@Autowired
	private PayChannelService payChannelService;

	@Autowired
	private HaPayOrderService payOrderService;

	/**
	 * 微信的统一下单
	 * 
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/unifiedOrder")
	public String payWxUnifiedOrder(@RequestParam String jsonParam) {
		JSONObject paramObj = JSON.parseObject(new String(HaBase64.decode(jsonParam)));
		PayOrder payOrder = paramObj.getObject("payOrder", PayOrder.class);
		String tradeType = paramObj.getString("tradeType");
		String logPrefix = "【微信支付统一下单】";
		String mchId = payOrder.getMchId();
		String channelId = payOrder.getChannelId();
		MchInfo mchInfo = mchInfoService.selectByMchId(mchId);
		String resKey = mchInfo == null ? "" : mchInfo.getResKey();
		if ("".equals(resKey))
			return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, "",
					PayConstants.RETURN_VALUE_FAIL, PayEnum.ERR_0001));
		PayChannel payChannel = payChannelService.selectPayChannel(channelId, mchId);
		String configParam = payChannel.getParam();
		HaWxConfigImpl config;
		try {
			config = HaWxConfigImpl.getInstance(configParam, certRootPath);
			wxpay = new WXPay(config);
			HashMap<String, String> data = createUnifiedOrderRequest(payOrder, tradeType);
			Map<String, String> r = wxpay.unifiedOrder(data);
			String return_code = r.get("return_code");
			System.out.println(r);
			_log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrder.getPayOrderId(), r.get("prepay_id"), r.toString());
			if ("SUCCESS".equals(return_code)) {
				Map<String, Object> map = HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_SUCCESS, "",
						PayConstants.RETURN_VALUE_SUCCESS, null);
				map.put("payOrderId", payOrder.getPayOrderId());
				map.put("prepayId", r.get("prepay_id"));
				int result = payOrderService.updateStatus4Ing(payOrder.getPayOrderId(), r.get("prepay_id"));
				_log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrder.getPayOrderId(), r.get("prepay_id"), result);
				switch (tradeType) {
				case PayConstants.PAY_CHANNEL_WX_JSAPI: {
                    Map<String, String> payInfo = new HashMap<>();
                    String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());//String.valueOf(System.currentTimeMillis() / 1000);
                    String nonceStr =WXPayUtil.generateNonceStr();// String.valueOf(System.currentTimeMillis());
                    payInfo.put("appId", r.get("appid"));
                    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    payInfo.put("timeStamp", timestamp);
                    payInfo.put("nonceStr", nonceStr);
                    payInfo.put("package", "prepay_id=" + r.get("prepay_id"));
                    payInfo.put("signType", WXPayConstants.HMACSHA256);
                    payInfo.put("paySign", WXPayUtil.generateSignature(payInfo, config.getKey(),WXPayConstants.SignType.HMACSHA256));
                    map.put("payParams", payInfo);
                    break;
                }
                case PayConstants.PAY_CHANNEL_WX_MWEB : {
                    map.put("payUrl", r.get("mweb_url"));    // h5支付链接地址
                    break;
                }
				}
				return HaPayUtil.makeRetData(map, resKey);
			} else {
				return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_SUCCESS, "",
						PayConstants.RETURN_VALUE_FAIL, "0111", "调用微信支付失败," + r.toString()));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_SUCCESS, "",
					PayConstants.RETURN_VALUE_FAIL, "0111", "调用微信支付失败," + e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_SUCCESS, "",
					PayConstants.RETURN_VALUE_FAIL, "0111", "调用微信支付失败," + e.getMessage()));
		}

	}

	/**
	 * 构建微信统一下单请求数据
	 * 
	 * @param payOrder
	 * @param wxPayConfig
	 * @return
	 */
	HashMap<String, String> createUnifiedOrderRequest(PayOrder payOrder, String tradeType) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("device_info", payOrder.getDevice());
		data.put("body", payOrder.getBody());
		data.put("detail", payOrder.getSubject());
		data.put("out_trade_no", payOrder.getPayOrderId());
		
		data.put("fee_type", "CNY");
		data.put("total_fee", payOrder.getAmount().toString());
		data.put("spbill_create_ip", payOrder.getClientIp());
		data.put("notify_url", notify_url);
		switch (tradeType) {
		case PayConstants.PAY_CHANNEL_WX_JSAPI:
			data.put("openid", JSON.parseObject(payOrder.getExtra()).getString("openId"));
			data.put("trade_type", PayConstants.WxConstant.TRADE_TYPE_JSPAI);
			break;
		}
		return data;
	}

}
