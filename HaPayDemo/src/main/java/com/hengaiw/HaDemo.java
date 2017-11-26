package com.hengaiw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({    
    "file:./config/db.properties", //数据库配置
    "file:./config/mchInfo.properties", //数据库配置
    "file:./config/freemarker.properties" //数据库配置  
})    
//@PropertySource(value={"file:./config/ha-demo.properties"})
public class HaDemo extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HaDemo.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.listeners();
        return application.sources(applicationClass);
    }

    private static Class<HaDemo> applicationClass = HaDemo.class;

}
