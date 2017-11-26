package com.hengaiw.base.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.model.dao.model.MchInfo;
import com.hengaiw.model.dao.model.PayChannel;
import com.hengaiw.model.service.MchInfoService;
import com.hengaiw.pub.constant.PayReturnCodeEnum;
import com.hengaiw.pub.utils.HaBase64;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;
/**
 * 商户相关操作接口类
 * @author jianhuizhang
 *
 */
@RestController
@RequestMapping(value = "/mchInfo")
public class PayMchInfoController {
   private final HaLog _log = HaLog.getLog(PayMchInfoController.class);
   @Autowired
	private MchInfoService mchInfoService;

	@RequestMapping(value = "/select")
	public String selectMchInfo(@RequestParam String jsonParam) {
        String logPre = "【查询商户信息】";
		_log.info("====== 开始查询商户信息请求 ======");
		if (StringUtils.isBlank(jsonParam)) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000002);
		}
		JSONObject paramObj = JSON.parseObject(new String(HaBase64.decode(jsonParam)));
		String mchId = paramObj.getString("mchId");
		_log.info("{}查询商户信息，参数mchId={}",logPre,mchId);
		MchInfo mchInfo = mchInfoService.selectByMchId(mchId);
		_log.info("{}查询商户信息,结果:mchInfo={}",logPre,JSON.toJSON(mchInfo));
		if (mchInfo==null) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000003);
		}
		_log.info("====== 结束查询商户信息请求 ======");
		return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_SUCCESS, JSON.toJSON(mchInfo));
	}
}
