package com.hengaiw.pay.wx.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengaiw.pay.wx.sdk.IWXPayDomain;
import com.hengaiw.pay.wx.sdk.WXPayConfig;

public class HaWxConfigImpl extends WXPayConfig {

	private byte[] certData;
	private String appId;
	private String mchId;
	private String key;

	private static HaWxConfigImpl INSTANCE;
	private String certName;

	private HaWxConfigImpl(String configParam, String certRootPath) throws IOException {
		Assert.notNull(configParam, "init wxpay config error");
		JSONObject paramObj = JSON.parseObject(configParam);
		this.appId = paramObj.getString("appId");
		this.mchId = paramObj.getString("mchId");
		this.key = paramObj.getString("key");
		this.certName = paramObj.getString("certName");
		String certPath = certRootPath + certName;
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
		// return this;

	}

	public static HaWxConfigImpl getInstance(String configParam, String certRootPath) throws Exception {
		if (INSTANCE == null) {
			synchronized (HaWxConfigImpl.class) {
				if (INSTANCE == null) {
					INSTANCE = new HaWxConfigImpl(configParam, certRootPath);
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return appId;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return mchId;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		ByteArrayInputStream certBis;
		certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	public int getHttpConnectTimeoutMs() {
		return 2000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	public IWXPayDomain getWXPayDomain() {
		return WXPayDomainSimpleImpl.instance();
	}

	public String getPrimaryDomain() {
		return "api.mch.weixin.qq.com";
	}

	public String getAlternateDomain() {
		return "api2.mch.weixin.qq.com";
	}

	@Override
	public int getReportWorkerNum() {
		return 1;
	}

	@Override
	public int getReportBatchSize() {
		return 2;
	}

}
