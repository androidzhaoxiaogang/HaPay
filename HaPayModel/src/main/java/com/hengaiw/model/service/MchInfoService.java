package com.hengaiw.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hengaiw.model.dao.mapper.MchInfoMapper;
import com.hengaiw.model.dao.model.MchInfo;

/**
 * @Description:

 */
@Component
public class MchInfoService {

    @Autowired
    private MchInfoMapper mchInfoMapper;

    public MchInfo selectByMchId(String mchId) {
        return mchInfoMapper.selectByMchId(mchId);
    }

}
