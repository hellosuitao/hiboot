package com.ruixun.hiboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ruixun.hiboot.mapper")
//@ContextConfiguration("classpath:applicationContext.xml")
public class HibootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibootApplication.class, args);
    }

}
