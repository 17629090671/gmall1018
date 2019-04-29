package com.atguigu.gmall1018.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;
//@ComponentScan是组件扫描注解，用来扫描@Controller  @Service  @Repository这类,主要就是
// 定义扫描的路径从中找出标志了需要装配的类到Spring容器中
//其次，@MapperScan 是扫描mapper类的注解,就不用在每个mapper类上加@MapperScan了
//这两个注解是可以同时使用的。

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall1018.user.mapper")
@ComponentScan(basePackages = "com.atguigu.gmall1018")
public class GmallUserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUserManagerApplication.class, args);
    }

}
