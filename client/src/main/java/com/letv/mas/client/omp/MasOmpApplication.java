package com.letv.mas.client.omp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.letv.mas.mapper")
@ServletComponentScan
public class MasOmpApplication{

    public static void main(String[] args) {
        SpringApplication.run(MasOmpApplication.class, args);
    }

}
