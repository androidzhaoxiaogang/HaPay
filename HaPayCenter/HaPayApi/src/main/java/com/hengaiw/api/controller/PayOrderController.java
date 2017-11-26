package com.hengaiw.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.api.serviceclient.PayWxServiceClient;
import com.hengaiw.api.serviceclient.PayBaseServiceClient;
import com.hengaiw.pub.constant.PayConstants;
import com.hengaiw.pub.utils.HaJsonFormat;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;
import com.hengaiw.pub.utils.HaSerial;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 支付订单,包括:统一下单,订单查询,补单等接口
 */
@RestController
@RequestMapping(value = "/pay/order")
public class PayOrderController extends PayBaseController{


	@Autowired
	private PayWxServiceClient payWxServiceClient;
	
	

	/**
	 * 统一下单接口: 1)先验证接口参数以及签名信息 2)验证通过创建支付订单 3)根据商户选择渠道,调用支付服务进行下单 4)返回下单数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/create")
	public String payOrder(@RequestParam String params) {
		_log.info("###### 开始接收商户统一下单请求 ######");
		String logPrefix = "【商户统一下单】";
		ServiceInstance instance = client.getLocalServiceInstance();
		_log.info("{}/pay/order/create, host:{}, service_id:{}, params:{}", logPrefix, instance.getHost(),
				instance.getServiceId(), params);
		try {
			JSONObject po = JSONObject.parseObject(params);
			JSONObject payOrder = null;
			// 验证参数有效性
			Object object = validateParams(po);
			if (object instanceof String) {
				_log.info("{}参数校验不通过:{}", logPrefix, object);
				return HaPayUtil.makeRetFail(
						HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, object.toString(), null, null));
			}
			if (object instanceof JSONObject)
				payOrder = (JSONObject) object;
			if (payOrder == null)
				return HaPayUtil
						.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, "支付中心下单失败", null, null));
			String result = payBaseServiceClient.createPayOrder(payOrder.toJSONString());
			_log.info("{}创建支付订单,结果:{}", logPrefix, result);
			if (StringUtils.isEmpty(result)) {
				return HaPayUtil
						.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, "创建支付订单失败", null, null));
			}
			JSONObject resObj = JSON.parseObject(result);
			if (resObj == null || !"1".equals(resObj.getString("result")))
				return HaPayUtil
						.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, "创建支付订单失败", null, null));
			String channelId = payOrder.getString("channelId");
			_log.info("请求支付渠道,ID:{}", channelId);
			switch (channelId) {

			case PayConstants.PAY_CHANNEL_WX_JSAPI:
				return payWxServiceClient
						.doWxUnifiedOrderReq(HaJsonFormat.getJsonParam(new String[] { "tradeType", "payOrder" },
								new Object[] { PayConstants.PAY_CHANNEL_WX_JSAPI, payOrder }));
			default:
				return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL,
						"不支持的支付渠道类型[channelId=" + channelId + "]", null, null));
			}
		} catch (Exception e) {
			_log.error(e, "");
			return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, "支付中心系统异常", null, null));
		}
	}

	/**
	 * 验证创建订单请求参数,参数通过返回JSONObject对象,否则返回错误文本信息
	 * 
	 * @param params
	 * @return
	 */
	private Object validateParams(JSONObject params) {
		// 验证请求参数,参数有问题返回错误提示
		String errorMessage;
		// 支付参数
		String mchId = params.getString("mchId"); // 商户ID
		String mchOrderNo = params.getString("mchOrderNo"); // 商户订单号
		String channelId = params.getString("channelId"); // 渠道ID
		String amount = params.getString("amount"); // 支付金额（单位分）
		String currency = params.getString("currency"); // 币种
		String clientIp = params.getString("clientIp"); // 客户端IP
		String device = params.getString("device"); // 设备
		String extra = params.getString("extra"); // 特定渠道发起时额外参数
		String param1 = params.getString("param1"); // 扩展参数1
		String param2 = params.getString("param2"); // 扩展参数2
		String notifyUrl = params.getString("notifyUrl"); // 支付结果回调URL
		String sign = params.getString("sign"); // 签名
		String subject = params.getString("subject"); // 商品主题
		String body = params.getString("body"); // 商品描述信息
		// 验证请求参数有效性（必选项）
		if (StringUtils.isBlank(mchId)) {
			errorMessage = "request params[mchId] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(mchOrderNo)) {
			errorMessage = "request params[mchOrderNo] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(channelId)) {
			errorMessage = "request params[channelId] error.";
			return errorMessage;
		}
		if (!NumberUtils.isNumber(amount)) {
			errorMessage = "request params[amount] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(currency)) {
			errorMessage = "request params[currency] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(notifyUrl)) {
			errorMessage = "request params[notifyUrl] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(subject)) {
			errorMessage = "request params[subject] error.";
			return errorMessage;
		}
		if (StringUtils.isBlank(body)) {
			errorMessage = "request params[body] error.";
			return errorMessage;
		}
		// 根据不同渠道,判断extra参数
		switch (channelId) {

		case PayConstants.PAY_CHANNEL_WX_JSAPI:
			if (StringUtils.isEmpty(extra)) {
				errorMessage = "request params[extra] error.";
				return errorMessage;
			}
			JSONObject extraObject = JSON.parseObject(extra);
			String openId = extraObject.getString("openId");
			if (StringUtils.isBlank(openId)) {
				errorMessage = "request params[extra.openId] error.";
				return errorMessage;
			}
			break;
		default:
			return HaPayUtil.makeRetFail(HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL,
					"不支持的支付渠道类型[channelId=" + channelId + "]", null, null));
		}
		
		// 签名信息
		if (StringUtils.isEmpty(sign)) {
			errorMessage = "request params[sign] error.";
			return errorMessage;
		}

		// 验证商户信息和签名
		Object valResult = validateMchInfoParams(params);
		if (valResult instanceof String) {
			errorMessage=valResult.toString();
			return errorMessage;
		}
		//验证渠道信息
		valResult = validateChannel(channelId,mchId);
		if (valResult instanceof String) {
			errorMessage=valResult.toString();
			return errorMessage;
		}
		// 验证参数通过,返回JSONObject对象
		JSONObject payOrder = new JSONObject();
		payOrder.put("payOrderId", HaSerial.getPay());
		payOrder.put("mchId", mchId);
		payOrder.put("mchOrderNo", mchOrderNo);
		payOrder.put("channelId", channelId);
		payOrder.put("amount", Long.parseLong(amount));
		payOrder.put("currency", currency);
		payOrder.put("clientIp", clientIp);
		payOrder.put("device", device);
		payOrder.put("subject", subject);
		payOrder.put("body", body);
		payOrder.put("extra", extra);
		payOrder.put("channelMchId", payChannel.getString("channelMchId"));
		payOrder.put("param1", param1);
		payOrder.put("param2", param2);
		payOrder.put("notifyUrl", notifyUrl);
		return payOrder;
	}

}
