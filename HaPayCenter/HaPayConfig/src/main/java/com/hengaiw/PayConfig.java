package com.hengaiw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
//@PropertySource(value={"file:./config/payconfig.yml"})
public class PayConfig {
	public static void main(String[] args) {
        SpringApplication.run(PayConfig.class, args);
    }
}
