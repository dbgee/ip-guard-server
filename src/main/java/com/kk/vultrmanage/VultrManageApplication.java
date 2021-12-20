package com.kk.vultrmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VultrManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(VultrManageApplication.class, args);
    }

}
