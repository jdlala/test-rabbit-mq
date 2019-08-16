package com.example.testrabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.testrabbitmq")
@MapperScan("com.example.testrabbitmq.mapper")
public class TestRabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestRabbitMqApplication.class, args);
    }

}
