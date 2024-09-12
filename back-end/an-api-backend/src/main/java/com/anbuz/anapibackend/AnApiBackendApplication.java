package com.anbuz.anapibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnApiBackendApplication.class, args);
    }

}
