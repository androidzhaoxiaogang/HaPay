package com.hengaiw.test.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hengaiw.model.dao.model.MchInfo;
import com.hengaiw.model.service.MchInfoService;


public class TestModel {
	
	@Autowired
	private MchInfoService mchInfoService;
	@Test
	public void testMchInfo() {
		MchInfo mchInfo=mchInfoService.selectByMchId("20001223");
		System.out.println(mchInfo.getMchId()+mchInfo.getReqKey());
	}
}
