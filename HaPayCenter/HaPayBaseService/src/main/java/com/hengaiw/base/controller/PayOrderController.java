package com.hengaiw.base.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.model.dao.model.MchInfo;
import com.hengaiw.model.dao.model.PayOrder;
import com.hengaiw.model.service.HaPayOrderService;
import com.hengaiw.pub.constant.PayReturnCodeEnum;
import com.hengaiw.pub.utils.HaBase64;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;

/**
 * 订单相关操作接口
 * 
 * @author jianhuizhang
 *
 */
@RestController
@RequestMapping(value = "/order")
public class PayOrderController {
	private final HaLog _log = HaLog.getLog(PayOrderController.class);
	@Autowired
	private HaPayOrderService payOrderService;

	/**
	 * 查询订单信息接口
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/select")
	public String selectOrder(@RequestParam String jsonParam) {
        String logPre = "【查询订单信息】";
		_log.info("====== 开始查询订单信息请求 ======");
		if (StringUtils.isBlank(jsonParam)) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000002);
		}
		JSONObject paramObj = JSON.parseObject(new String(HaBase64.decode(jsonParam)));
		String payOrderId = paramObj.getString("payOrderId");
		_log.info("{}查询订单信息，参数param={}",logPre,new String(HaBase64.decode(jsonParam)));
		PayOrder payOrder = payOrderService.selectPayOrder(payOrderId);
		_log.info("{}查询商户信息,结果:payOrder={}",logPre,payOrder.toString());
		if (payOrder!=null) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000003);
		}
		_log.info("====== 结束查询订单信息请求 ======");
		return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_SUCCESS, JSON.toJSON(payOrder));
	}
	/**
	 * 创建订单接口
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/create")
	public String createPayOrder(@RequestParam String jsonParam) {
		String logPre = "【添加订单信息】";
		_log.info("====== 开始添加订单信息请求 ======");
		if (StringUtils.isBlank(jsonParam)) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000002);
		}
		try {
			_log.info("{}添加订单参数{}",logPre,new String(HaBase64.decode(jsonParam)));
			PayOrder payOrder = JSON.parseObject(new String(HaBase64.decode(jsonParam)), PayOrder.class);
			int result = payOrderService.createPayOrder(payOrder);
			_log.info("====== 结束添加订单信息请求 ======");
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_SUCCESS, JSON.toJSON(result));
		} catch (Exception e) {
			return HaPayUtil.createRetJson(PayReturnCodeEnum.PAY_ERR_000001);
		}
	}
}
