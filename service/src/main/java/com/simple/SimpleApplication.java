package com.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages="com.simple")
@EnableFeignClients(basePackages = {"com.simple.mail"})
@EnableTransactionManagement
@PropertySources({ @PropertySource("classpath:mybatis/mybatis.properties") })
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}

