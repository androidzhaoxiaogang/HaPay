package com.hengaiw;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.hengaiw.filter.AccessFilter;

@EnableZuulProxy
@SpringCloudApplication
public class PayGateWay {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayGateWay.class).web(true).run(args);
	}

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
}
