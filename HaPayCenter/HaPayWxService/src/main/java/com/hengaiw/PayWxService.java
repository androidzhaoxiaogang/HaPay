package com.hengaiw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource({    
	"file:./config/db.properties", //数据库配置
})    
public class PayWxService {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayWxService.class).web(true).run(args);
	}
}
