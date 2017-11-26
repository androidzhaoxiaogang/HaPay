package com.hengaiw.test.wx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import com.hengaiw.pay.wx.config.HaWxConfigImpl;
import com.hengaiw.pay.wx.sdk.WXPay;
import com.hengaiw.pay.wx.sdk.WXPayUtil;

public class TestWx {
	
	private static String certRootPath="/Users/jianhuizhang/Downloads/cert/";
	private static WXPay wxpay;
	
	public static void main(String[] args) throws Exception {
		Logger _log=WXPayUtil.getLogger();
		_log.info("sfsfsdfdsfsdffdssfd");
		String configParam = "{\"mchId\":\"1490527632\",\"appId\":\"wx9ed8fbe06bde3bad\",\"key\":\"EIOHGdsgihdsiogs20ds1g2sdggsd211\",\"certLocalPath\":\"\",\"certName\":\"apiclient_cert.p12\"}";
		HaWxConfigImpl config;
		try {
			config = HaWxConfigImpl.getInstance(configParam, certRootPath);
			System.out.println(config.getKey());
			wxpay = new WXPay(config);
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("body", "腾讯充值中心-QQ会员充值");
			data.put("out_trade_no", "201613091059590000003233-asd008");
			data.put("device_info", "");
			data.put("fee_type", "CNY");
			data.put("total_fee", "1");
			data.put("spbill_create_ip", "123.12.12.123");
			data.put("notify_url", "http://test.letiantian.me/wxpay/notify");
			data.put("trade_type", "MWEB");
			data.put("product_id", "12");
			data.put("openid", "oZ2FfwvgX8o5KoImIG8h6IVuvxB8");
			//data.put("time_expire", "20170112104120");

			try {
				Map<String, String> r = wxpay.unifiedOrder(data);
				System.out.println(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// return config.getAppID();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
