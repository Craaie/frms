package com.formssi.frms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.formssi.frms.*.dao")
@SpringBootApplication
public class FrmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrmsApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    Frms启动成功      ヾ(◍°∇°◍)ﾉﾞ\n");
    }
}
