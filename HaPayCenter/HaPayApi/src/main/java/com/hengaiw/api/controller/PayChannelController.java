package com.hengaiw.api.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.api.serviceclient.PayBaseServiceClient;
import com.hengaiw.pub.constant.PayConstants;
import com.hengaiw.pub.constant.PayReturnCodeEnum;
import com.hengaiw.pub.utils.HaJsonFormat;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;

/**
 * 对外提供通道相关的操作接口
 * 
 * @author jianhuizhang
 *
 */
@RestController
@RequestMapping(value = "/pay/channel")
public class PayChannelController extends PayBaseController{

	@RequestMapping(value = "/select")
	public String selectPayChannel(@RequestParam String params) {
		String logPrefix = "【查询通道信息】";
		ServiceInstance instance = client.getLocalServiceInstance();
		_log.info("{}/pay/channel/select, host:{}, service_id:{}, params:{}", logPrefix, instance.getHost(),
				instance.getServiceId(), params);
		// 校验请求参数是否合法
		JSONObject paramObj = JSONObject.parseObject(params);
		System.out.println(paramObj.getString("mchId"));
		// 验证参数有效性
		Object valResult = validateMchInfoParams(paramObj);
		if (valResult instanceof String) {
			_log.info("{}参数校验不通过:{}", logPrefix, valResult);
			return HaPayUtil.makeRetFail(
					HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_FAIL, valResult.toString(), null, null));
		}
		String mchId = paramObj.getString("mchId"); // 商户ID
		String channelId = paramObj.getString("channelId"); // 渠道ID

		// 获取通道信息
		
		String errorMessage="";
		String retStr = payBaseServiceClient.selectPayChannel(
				HaJsonFormat.getJsonParam(new String[] { "channelId", "mchId" }, new String[] { channelId, mchId }));
		JSONObject retObj = JSON.parseObject(retStr);
		_log.info("{}通道返回结果:{}", logPrefix, retStr);
		if (PayReturnCodeEnum.PAY_SUCCESS.getCode().equals(retObj.getString("retCode"))) {
			payChannel = retObj.getJSONObject("result");
			if (payChannel == null) {
				errorMessage = "未找到通道[mchId=" + mchId + "]信息";
				return errorMessage;
			}
			if (payChannel.getByte("state") != 1) {
				errorMessage = " 通道[mchId=" + mchId + "] 状态异常";
				return errorMessage;
			}
		} else {
			errorMessage = "未找到通道[mchId=" + mchId + "]信息";
			return errorMessage;
		}
		 Map<String, Object> map = HaPayUtil.makeRetMap(PayConstants.RETURN_VALUE_SUCCESS, "", PayConstants.RETURN_VALUE_SUCCESS, null);
         map.put("result", payChannel);
		return HaPayUtil.makeRetData(map, mchInfo.getString("resKey"));
	}
	
}
