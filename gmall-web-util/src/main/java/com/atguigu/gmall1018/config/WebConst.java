package com.atguigu.gmall1018.config;

/**
 * @author enlong zhang
 * @date 2019/4/26 - 19:48
 */
public interface WebConst {
    //登录页面
    public final static String LOGIN_URL="http://passport.atguigu.com/index";
    //认证接口
    public final static String VERIFY_URL="http://passport.atguigu.com/verify";
    //cookie的有效时间：默认给7天
    public final static int cookieMaxAge=7*24*3600;
}