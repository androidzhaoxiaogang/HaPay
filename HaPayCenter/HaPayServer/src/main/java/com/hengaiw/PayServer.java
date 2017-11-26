package com.hengaiw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PayServer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayServer.class).web(true).run(args);
	}

}
