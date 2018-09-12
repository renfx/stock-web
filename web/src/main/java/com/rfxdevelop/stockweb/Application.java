package com.rfxdevelop.stockweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.rfxdevelop.stockweb")
@ServletComponentScan(basePackages = "com.rfxdevelop.basicmvc.filter")
@MapperScan("com.rfxdevelop.basicmvc.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
