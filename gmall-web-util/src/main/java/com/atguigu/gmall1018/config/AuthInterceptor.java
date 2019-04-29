package com.atguigu.gmall1018.config;


import com.alibaba.fastjson.JSON;
import com.atguigu.gmall1018.util.HttpClientUtil;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    // 进入控制器之前，执行！
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录的时候 将token 写入cookie
        String token = request.getParameter("newToken");
        // 写cookie！
        if (token!=null){
            // 使用cookie 的工具类
            CookieUtil.setCookie(request,response,"token",token,WebConst.cookieMaxAge,false);
        }
        // 当访问非登录模块的时候，
        if (token==null){
            // 从cookie 中获取token
            token = CookieUtil.getCookieValue(request, "token", false);
        }

        // 知道方法上是否有注解@LoginRequire
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取请求的方法，并获取方法上的注解
        LoginRequire methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequire.class);
        if (methodAnnotation!=null){
            // 开始准备认证 verify ();  在passport-web 中 web-util 访问 passport-web 跨域 ： @CrossOrigin， httpclient，jsonp
            // 获取salt
            String salt = request.getHeader("X-forwarded-for");
            // httpclient 远程调用 doget，dopost

            String result = HttpClientUtil.doGet(WebConst.VERIFY_URL + "?token=" + token + "&salt=" + salt);

            if ("success".equals(result)){
                // 保存用户Id
                Map map = getUserMapByToken(token);
                // 通过key nickName 获取用户昵称
                String userId = (String) map.get("userId");
                //  保存作用域
                request.setAttribute("userId",userId);
                // 用户登录进行放行！
                return true;
            }else {
                // 还需要看一下当前注解中的属性autoRedirect
                if (methodAnnotation.autoRedirect()){
                    // 跳转登录页面！
                    // http://passport.atguigu.com/index?originUrl=http%3A%2F%2Fitem.gmall.com%2F39.html
                    String requestURL  = request.getRequestURL().toString(); // http://item.gmall.com/39.html
                    // 进行编码
                    String encodeURL = URLEncoder.encode(requestURL, "UTF-8"); // http%3A%2F%2Fitem.gmall.com%2F39.html
                    // 页面跳转
                    response.sendRedirect(WebConst.LOGIN_URL+"?originUrl="+encodeURL);

                    return false;
                }
            }
        }
        return true;
    }

    // 解密token的
    private Map getUserMapByToken(String token) {
        // eyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6IkF0Z3VpZ3UiLCJ1c2VySWQiOiIxIn0.XzRrXwDhYywUAFn-ICLJ9t3Xwz7RHo1VVwZZGNdKaaQ
        String ntoken = StringUtils.substringBetween(token, ".");
        // jwt通过base64 编码的，使用base64 发解码
        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
        // 字节数组
        byte[] decode = base64UrlCodec.decode(ntoken);
        // 字节数组，与map 集合转换，不能直接转换，将byte[] decode 变成字符串
        String mapStr = null;
        try {
            mapStr = new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map map = JSON.parseObject(mapStr, Map.class);

        return map;


    }
}


