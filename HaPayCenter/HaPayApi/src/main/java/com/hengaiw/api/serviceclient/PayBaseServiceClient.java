package com.hengaiw.api.serviceclient;

import com.hengaiw.pub.utils.HaBase64;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PayBaseServiceClient {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 创建支付订单
     * @param jsonParam
     * @return
     */
    @HystrixCommand(fallbackMethod = "createPayOrderFallback")
    public String createPayOrder(String jsonParam) {
        return restTemplate.getForEntity("http://PAYBASESERVICE/order/create?jsonParam=" + HaBase64.encode(jsonParam.getBytes()), String.class).getBody();
    }

    public String createPayOrderFallback(String jsonParam) {
        return "error";
    }

    /**
     * 查询支付订单
     * @param jsonParam
     * @return
     */
    @HystrixCommand(fallbackMethod = "queryPayOrderFallback")
    public String queryPayOrder(String jsonParam) {
        return restTemplate.getForEntity("http://PAYBASESERVICE/order/select?jsonParam=" + HaBase64.encode(jsonParam.getBytes()), String.class).getBody();
    }

    public String queryPayOrderFallback(String jsonParam) {
        return "error";
    }
    /**
     * 查询商户信息
     * @param jsonParam
     * @return
     */
    @HystrixCommand(fallbackMethod = "selectMchInfoFallback")
    public String selectMchInfo(String jsonParam) {
        return restTemplate.getForEntity("http://PAYBASESERVICE/mchInfo/select?jsonParam=" + HaBase64.encode(jsonParam.getBytes()), String.class).getBody();
    }

    
    public String selectMchInfoFallback(String jsonParam) {
        return "error";
    }

    /**
     * 查询通道信息
     * @param jsonParam
     * @return
     */
    @HystrixCommand(fallbackMethod = "selectPayChannelFallback")
    public String selectPayChannel(String jsonParam) {
        return restTemplate.getForEntity("http://PAYBASESERVICE/payChannel/select?jsonParam=" + HaBase64.encode(jsonParam.getBytes()), String.class).getBody();
    }

    public String selectPayChannelFallback(String jsonParam) {
        return "error";
    }
}