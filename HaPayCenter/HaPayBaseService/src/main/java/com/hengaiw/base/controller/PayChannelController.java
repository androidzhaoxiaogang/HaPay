package com.hengaiw.base.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.model.dao.model.PayChannel;
import com.hengaiw.model.dao.model.PayOrder;
import com.hengaiw.model.service.PayChannelService;
import com.hengaiw.pub.constant.PayReturnCodeEnum;
import com.hengaiw.pub.utils.HaBase64;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;
/**
 * 支付通道相关操作接口
 * @author jianhuizhang
 *
 */
@RestController
@RequestMapping(value = "/payChannel")
public class PayChannelController {
	private final HaLog _log = HaLog.getLog(PayChannelController.class);
	@Autowired
	private PayChannelService payChannelService;

	/**
	 * 查询通道相关信息
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/select")
	public String selectPayChannel(@RequestParam String jsonParam) {
		String logPre = "【查询通道信息】";
		_log.info("====== 开始查询通道信息请求 ======");
		if (StringUtils.isBlank(jsonParam)) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000002);
		}
		JSONObject paramObj = JSON.parseObject(new String(HaBase64.decode(jsonParam)));
		String channelId = paramObj.getString("channelId");
		String mchId = paramObj.getString("mchId");
		_log.info("{}查询通道信息，参数channelId={},mchId={}",logPre,channelId,mchId);
		PayChannel payChannel = payChannelService.selectPayChannel(channelId, mchId);
		_log.info("{}查询通道信息,结果:payChannel={}",logPre,JSON.toJSON(payChannel));
		if (payChannel==null) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000003);
		}
		_log.info("====== 结束查询通道信息请求 ======");
		return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_SUCCESS, JSON.toJSON(payChannel));
	}
	/**
	 * 创建通道接口
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/create")
	public String createChannel(@RequestParam String jsonParam) {
		String logPre = "【添加通道信息】";
		_log.info("====== 开始添加通道信息请求 ======");
		if (StringUtils.isBlank(jsonParam)) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000002);
		}
		try {
			_log.info("{}添加订单参数{}",logPre,new String(HaBase64.decode(jsonParam)));
			PayChannel payChannel = JSON.parseObject(new String(HaBase64.decode(jsonParam)), PayChannel.class);
			int result = payChannelService.createChannle(payChannel);
			_log.info("====== 结束添加通道信息请求 ======");
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_SUCCESS, JSON.toJSON(result));
		} catch (Exception e) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000001);
		}
	}
}
