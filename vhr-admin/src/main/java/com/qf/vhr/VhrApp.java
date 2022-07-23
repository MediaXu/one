package com.qf.vhr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.qf.vhr.framework.mapper")
public class VhrApp {
    public static void main(String[] args) {
        SpringApplication.run(VhrApp.class,args);
    }
}
