package com.hengaiw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource({    
    "file:./config/db.properties"//数据库配置
})    
public class PayBaseService {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayBaseService.class).web(true).run(args);
	}
}
