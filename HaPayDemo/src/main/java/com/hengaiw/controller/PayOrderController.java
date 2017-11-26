package com.hengaiw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.model.dao.model.GoodsOrder;
import com.hengaiw.pub.utils.AmountUtil;
import com.hengaiw.pub.utils.HaLog;
import com.hengaiw.pub.utils.HaPayUtil;
import com.hengaiw.pub.utils.PayDigestUtil;
import com.hengaiw.service.GoodsOrderService;
import com.hengaiw.util.Constant;
import com.hengaiw.util.DateUtil;
import com.hengaiw.util.IPUtility;
import com.hengaiw.util.OAuth2RequestParamHelper;
import com.hengaiw.util.vx.WxApi;
import com.hengaiw.util.vx.WxApiClient;

@Controller
@RequestMapping("/pay")
public class PayOrderController {
	private final static HaLog _log = HaLog.getLog(PayOrderController.class);
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Value("${wx.appId}")
    private   String appId;
	@Value("${wx.appSecret}")
    private   String appSecret;
	@Value("${mch.mchId}")
	private   String mchId;
	@Value("${mch.reqKey}")
	private  String reqKey;
	@Value("${mch.resKey}")
	private  String resKey;
	@Value("${mch.notifyUrl}")
	private  String notifyUrl;
	@Value("${mch.goodsId}")
	private  String goodsId;
	@Value("${mch.goodsName}")
	private  String goodsName;
	@Value("${mch.userId}")
	private  String userId;
	@Value("${pay.createOrderUrl}")
	private  String createOrderUrl;
	@Value("${site.baseUrl}")
	private  String baseUrl;
	private  AtomicLong seq = new AtomicLong(0L);
	private final  String QR_PAY_URL = "/pay/qrPay.html";
    private final  String GetOpenIdURL ="/pay/getOpenId.html";
  
	/**
	 * 生成二维码页面
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping("/openQrPay.html")
	public String openQrPay(ModelMap model) {
		model.put("baseUrl", baseUrl);
		return "openQrPay";
	}

	/**
	 * 扫码支付处理连接
	 * 
	 * @param model
	 * @param request
	 * @param amount
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/qrPay.html")
	public String qrPay(ModelMap model, HttpServletRequest request, Long amount) {
		// 设置流程性的日志前缀
		String logPre = "【微信支付宝收银台扫码测试】";
		_log.info("====== 开始接收二维码扫码支付请求 ======");
		String ua = request.getHeader("User-Agent");// 获取用户的User-Agent，判断用户使用的是微信或是支付宝扫码
		_log.info("{}接收参数:amount={},ua={}", logPre, amount, ua);
		String view = "qrPay";
		String client = "alipay";
		String channelId = "ALIPAY_WAP";
		if(ua.contains("Alipay")) {//是否使用支付宝扫描
			_log.info("{}支付宝扫码支付", logPre);
			client = "alipay";
            channelId = "ALIPAY_WAP";
		}else if(ua.contains("MicroMessenger")) {//是否使用微信扫描
			_log.info("{}微信扫码支付", logPre);
			client = "wx";
            channelId = "WX_JSAPI";
		}else {//其他扫描方式
			String errorMessage = "暂时不支持的扫描方式！";
            _log.info("{}扫描出错信息：{}", logPre, errorMessage);
            model.put("result", "failed");
            model.put("resMsg", errorMessage);
            return view;
		}
		//定义插入订单的对象
		Map params = new HashMap<>();
		GoodsOrder goodsOrder = null;
		Map<String, String> orderMap = null;
		params.put("channelId", channelId);
		//如果是微信扫码，则需要先获取到用户的openid参数用于后续支付
		if(client.equals("wx")) {
			String openId = request.getParameter("openId");
			if (StringUtils.isBlank(openId)) {//如果地址中不带openId，则跳转到获取openId的连接地址
				String redirectUrl = baseUrl+QR_PAY_URL + "?amount=" + amount;
                String url = baseUrl+GetOpenIdURL + "?redirectUrl=" + redirectUrl;
                _log.info("{}跳转到获取微信openId的URL={}", logPre,url);
                return "redirect:" + url;
			}else {
				params.put("openId", openId);
			}
		}
		//商户下订单
		goodsOrder = createGoodsOrder( amount);
        //支付中心下订单
        orderMap =createPayOrder(goodsOrder, params);
		model.put("goodsOrder", goodsOrder);
        model.put("amount", AmountUtil.convertCent2Dollar(goodsOrder.getAmount()+""));
        if(orderMap != null) {
            model.put("orderMap", orderMap);
            String payOrderId = orderMap.get("payOrderId");
            GoodsOrder go = new GoodsOrder();
            go.setGoodsOrderId(goodsOrder.getGoodsOrderId());
            go.setPayOrderId(payOrderId);
            go.setChannelId(channelId);
            int ret = goodsOrderService.update(go);
            _log.info("{}修改商品订单,返回:{}", logPre,ret);
        }
        model.put("client", client);
        _log.info("====== 结束接收二维码扫码支付请求 ======");
        return view;

	}
	
	
	/**
     * 获取code
     * @return
     */
    @RequestMapping("/getOpenId.html")
    public void getOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException {
    		String logPre = "【微信获取openId】";
		_log.info("====== 开始进行获取openId的请求 ======");
        String redirectUrl = request.getParameter("redirectUrl");
        String code = request.getParameter("code");
        String openId = "";
        if(!StringUtils.isBlank(code)){//如果request中包括code，则是微信回调
            try {
                openId = WxApiClient.getOAuthOpenId(appId, appSecret, code);
                _log.info("{}成功获取openId={}", logPre,openId);
            } catch (Exception e) {
                _log.error(e, "调用微信查询openId异常");
            }
            if(redirectUrl.indexOf("?") > 0) {
                redirectUrl += "&openId=" + openId;
            }else {
                redirectUrl += "?openId=" + openId;
            }
            _log.info("====== 结束获取openId的请求 ======");
            response.sendRedirect(redirectUrl);
        }else{//oauth获取code
            String redirectUrl4Vx =baseUrl+ GetOpenIdURL + "?redirectUrl=" + redirectUrl;
            String state = OAuth2RequestParamHelper.prepareState(request);
            String url = WxApi.getOAuthCodeUrl(appId, redirectUrl4Vx, "snsapi_base", state);
            _log.info("{}获取openId跳转URL={}",logPre, url);
            response.sendRedirect(url);
        }
    }
    
    /**
	 * 商户下单
	 * @param goodsId
	 * @param amount
	 * @return
	 */
    GoodsOrder createGoodsOrder(Long amount) {
		String logPre = "【商户下单】";
		_log.info("====== 开始商户下单请求 ======");
		// 先插入订单数据
		String goodsOrderId = String.format("%s%s%06d", "S", DateUtil.getSeqString(),
				(int) seq.getAndIncrement() % 1000000);
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setGoodsOrderId(goodsOrderId);
		goodsOrder.setGoodsId(goodsId);
		goodsOrder.setGoodsName(goodsName);
		goodsOrder.setAmount(amount);
		goodsOrder.setUserId(userId);
		goodsOrder.setStatus(Constant.GOODS_ORDER_STATUS_INIT);
		_log.info("{}下单参数:goodsId={},goodsName={},userId={}", logPre, goodsId,goodsName, userId);
		int result = goodsOrderService.addGoodsOrder(goodsOrder);
		_log.info("{}插入商品订单插入数据{},返回:{}", logPre,result);
		_log.info("====== 结束商户下单请求 ======");
		return goodsOrder;
	}
	/**
	 * 支付中心平台下单
	 * @param goodsOrder
	 * @param params
	 * @return
	 */
	Map createPayOrder(GoodsOrder goodsOrder, Map<String, Object> params) {
		String logPre = "【支付中心下单】";
		_log.info("====== 开始支付中心下单请求 ======");
		JSONObject paramMap = new JSONObject();
		paramMap.put("mchId", mchId); // 商户ID
		paramMap.put("mchOrderNo", goodsOrder.getGoodsOrderId()); // 商户订单号
		paramMap.put("channelId", params.get("channelId")); // 支付渠道ID, WX_NATIVE,ALIPAY_WAP
		paramMap.put("amount", goodsOrder.getAmount()); // 支付金额,单位分
		paramMap.put("currency", "cny"); // 币种, cny-人民币
		paramMap.put("clientIp", IPUtility.getLocalIP()); // 用户地址,IP或手机号
		paramMap.put("device", "WEB"); // 设备
		paramMap.put("subject", goodsOrder.getGoodsName());
		paramMap.put("body", goodsOrder.getGoodsName());
		paramMap.put("notifyUrl",notifyUrl ); // 回调URL
		paramMap.put("param1", ""); // 扩展参数1
		paramMap.put("param2", ""); // 扩展参数2
		JSONObject extra = new JSONObject();
		//根据支付方式添加附加参数
		if(params.get("channelId").toString().contains("WX")) {//如果是微信则添加openid参数
			extra.put("openId", params.get("openId"));
			paramMap.put("extra", extra.toJSONString()); // 附加参数
		}
		String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
		paramMap.put("sign", reqSign); // 签名
		String reqData = "params=" + paramMap.toJSONString();
		_log.info("{}支付中心下单参数:params={}", logPre, reqData);
		String result = HaPayUtil.call4Post(createOrderUrl + reqData);
		_log.info("{}支付中心下单，响应数据:{}", logPre, result);
		Map retMap = JSON.parseObject(result);
		if ("SUCCESS".equals(retMap.get("retCode"))) {
			// 验签
			String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
			String retSign = (String) retMap.get("sign");
			if (checkSign.equals(retSign)) {
				_log.info("====== 结束支付中心下单请求,验签成功 ======");
				return retMap;
			} else {
				_log.info("====== 结束支付中心下单请求,验签失败 ======");
				return null;
			}
		}else {
			_log.info("====== 结束支付中心下单请求,下单失败 ======");
			return null;
		}
		
		
	}
}
