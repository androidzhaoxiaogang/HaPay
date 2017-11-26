package com.hengaiw.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hengaiw.model.dao.mapper.PayChannelMapper;
import com.hengaiw.model.dao.model.PayChannel;
import com.hengaiw.model.dao.model.PayChannelExample;
import com.hengaiw.model.dao.model.PayOrder;

import java.util.List;

/**
 * @Description:
 
 */
@Component
public class PayChannelService {

    @Autowired
    private PayChannelMapper payChannelMapper;

    public PayChannel selectPayChannel(String channelId, String mchId) {
        PayChannelExample example = new PayChannelExample();
        PayChannelExample.Criteria criteria = example.createCriteria();
        criteria.andChannelIdEqualTo(channelId);
        criteria.andMchIdEqualTo(mchId);
        List<PayChannel> payChannelList = payChannelMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(payChannelList)) return null;
        return payChannelList.get(0);
    }
    public int createChannle(PayChannel payChannel) {
        return payChannelMapper.insertSelective(payChannel);
    }
}
