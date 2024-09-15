package com.anbuz.anapibackend;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDubbo
@DubboComponentScan
public class AnApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnApiBackendApplication.class, args);
    }

}
