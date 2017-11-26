package com.hengaiw.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;
import com.hengaiw.pub.utils.PayDigestUtil;

@Controller
@RequestMapping("/channel")
public class PayChannelController {
	private final static HaLog _log = HaLog.getLog(PayChannelController.class);
	@Value("${mch.reqKey}")
	private  String reqKey;
	@Value("${mch.resKey}")
	private  String resKey;
	@Value("${pay.selectChannel}")
    private String selectChannel_url;
	/**
	 * 测试渠道查询页面
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping("/testChannel.html")
	public String testChannel(ModelMap model, HttpServletRequest request) {
		System.out.println(selectChannel_url);
		String mchId=request.getParameter("mchId");
		String channelId=request.getParameter("channelId");
		if(!StringUtils.isBlank(mchId)) {
			JSONObject paramMap = new JSONObject();
			paramMap.put("mchId", mchId);
			paramMap.put("channelId", channelId);
			String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
			paramMap.put("sign", reqSign); // 签名
			String reqData = "params=" + paramMap.toJSONString();
			System.out.println(reqData);
			System.out.println(selectChannel_url);
			String result = HaPayUtil.call4Post(selectChannel_url + reqData);
			Map retMap = JSON.parseObject(result);
	        if("SUCCESS".equals(retMap.get("retCode"))) {
	            // 验签
	            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
	            String retSign = (String) retMap.get("sign");
	            if(checkSign.equals(retSign)) {
	                System.out.println("=========支付中心下单验签成功=========");
	            }else {
	                System.err.println("=========支付中心下单验签失败=========");
	                return null;
	            }
	        }
		}
		return "testChannel";
	}
}
