package com.atguigu.gmall1018.passport;

import com.atguigu.gmall1018.passport.config.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan("com.atguigu.gmall1018")
public class GmallPassportWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPassportWebApplication.class, args);
    }
    /**
     * @param key   自定义字符串
     * @param param 存储用户信息
     * @param salt  服务器的ip地址
     * @return
     */
//    @Test
//    public void test01(){
//        String key="atguigu";
//        String salt="192.168.18.129"; //salt
//        Map<String,Object> map = new HashMap();
//        map.put("userId","1001");
//        map.put("nickName","admin");
//        String token = JwtUtil.encode(key,map,salt);
//        System.out.println(token);
//        System.out.println("************************************");
//        Map<String, Object> stringObjectMap = JwtUtil.decode(token, key, salt);
//        System.out.println(stringObjectMap);
//
//    }
}
