package com.hengaiw.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.api.serviceclient.PayBaseServiceClient;
import com.hengaiw.pub.constant.PayReturnCodeEnum;
import com.hengaiw.pub.utils.HaJsonFormat;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;

public class PayBaseController {
	public final HaLog _log = HaLog.getLog(PayBaseController.class);
	@Autowired
	public DiscoveryClient client;
	@Autowired
	public PayBaseServiceClient payBaseServiceClient;
	public JSONObject mchInfo;
	public JSONObject payChannel;

	/**
	 * 检查商户信息和验证提交的数据是否正确，正确返回商户信息，否则返回错误信息
	 * 
	 * @param params
	 * @return
	 */
	public Object validateMchInfoParams(JSONObject params) {
		String mchId = params.getString("mchId");
		String errorMessage = "";
		if (StringUtils.isBlank(mchId)) {
			errorMessage = "请求参数错误，必须有商户ID";
			return errorMessage;
		}
		// 查询商户信息
		String retStr = payBaseServiceClient.selectMchInfo(HaJsonFormat.getJsonParam("mchId", mchId));
		_log.info("返回结果:{}", retStr);
		JSONObject retObj = JSON.parseObject(retStr);
		if (PayReturnCodeEnum.PAY_SUCCESS.getCode().equals(retObj.getString("retCode"))) {
			mchInfo = retObj.getJSONObject("result");
			if (mchInfo == null) {
				errorMessage = "返回商户[mchId=" + mchId + "]信息为空";
				return errorMessage;
			}
			if (mchInfo.getByte("state") != 1) {
				errorMessage = " 商户[mchId=" + mchId + "] 状态异常";
				return errorMessage;
			}
		} else {
			errorMessage = "返回商户[mchId=" + mchId + "]信息错误";
			return errorMessage;
		}

		String reqKey = mchInfo.getString("reqKey");
		if (StringUtils.isBlank(reqKey)) {
			errorMessage = "商户[mchId=" + mchId + "] 密钥为空";
			return errorMessage;
		}
		// 验证签名数据
		boolean verifyFlag = HaPayUtil.verifyPaySign(params, reqKey);
		if (!verifyFlag) {
			errorMessage = "商户[mchId=" + mchId + "]验签失败";
			return errorMessage;
		}
		return mchInfo;

	}

	/**
	 * 检查渠道信息是否正确，正确返回渠道信息，否则返回错误信息
	 * @param channelId
	 * @param mchId
	 * @return
	 */
	public Object validateChannel(String channelId, String mchId) {
		String errorMessage = "";
		// 查询商户对应的支付渠道
		String retStr = payBaseServiceClient.selectPayChannel(
				HaJsonFormat.getJsonParam(new String[] { "channelId", "mchId" }, new String[] { channelId, mchId }));
		JSONObject retObj = JSON.parseObject(retStr);
		if (PayReturnCodeEnum.PAY_SUCCESS.getCode().equals(retObj.getString("retCode"))) {
			payChannel = JSON.parseObject(retObj.getString("result"));
			if (payChannel == null) {
				errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
				return errorMessage;
			}
			if (payChannel.getByte("state") != 1) {
				errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
				return errorMessage;
			}
		} else {
			errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
			_log.info("查询渠道没有正常返回数据,code={},msg={}", retObj.getString("code"), retObj.getString("msg"));
			return errorMessage;
		}
		return payChannel;
	}
}
