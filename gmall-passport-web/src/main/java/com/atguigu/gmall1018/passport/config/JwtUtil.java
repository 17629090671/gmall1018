package com.atguigu.gmall1018.passport.config;

import io.jsonwebtoken.*;

import java.util.Map;

public class JwtUtil {
    /**
     * @param key   自定义字符串
     * @param param 存储用户信息
     * @param salt  服务器的ip地址
     * @return
     */
    public static String encode(String key, Map<String, Object> param, String salt) {
        if (salt != null) {
            key += salt;
        }
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, key);
        //将用户信息放入jwtBuilder
        jwtBuilder = jwtBuilder.setClaims(param);
        //jwtBuilder调用方法获取token

        String token = jwtBuilder.compact();
        return token;

    }

    /**
     * @param token ebcode生成的token
     * @param key   自定义字符串
     * @param salt  服务器ip地址
     * @return 解密token中的map存储的用户信息
     */
    public static Map<String, Object> decode(String token, String key, String salt) {
        Claims claims = null;
        if (salt != null) {
            key += salt;
        }
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            return null;
        }
        return claims;
    }

}
