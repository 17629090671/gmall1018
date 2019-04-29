package com.atguigu.gmall1018.passport.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall1018.bean.UserInfo;
import com.atguigu.gmall1018.passport.config.JwtUtil;
import com.atguigu.gmall1018.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;

/**
 * @author enlong zhang
 * @date 2019/4/24 - 10:03
 */
@Controller
public class PassportController {
    //@Reference跨项目使用
    @Reference
    private UserService userService;
    @Value("${token.key}")
    String key;
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        /**
         * originUrl写成originURL导致从页面获取不到
         */

        //获取导航栏输入的值
        //http://passport.atguigu.com/index?originUrl=https%3A%2F%2Fwww.jd.com%2F
        String originUrl = request.getParameter("originUrl");
        //将originUrl放入到页面中
        request.setAttribute("originUrl",originUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UserInfo userInfo, HttpServletRequest request){
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

        UserInfo info= userService.login(userInfo);
        if (info!=null){
            String salt = request.getHeader("X-forwarded-for");//获得的是本机ip
            Map<String, Object> map = new HashMap();
            map.put("userId", info.getId());
            map.put("nickName", info.getNickName());
            String token= JwtUtil.encode(key,map,salt);
            System.out.println("token"+token);
            //tokeneyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6IkF0Z3VpZ3UiLCJ1c2VySWQiOiIxIn0.MYyFX6Izns__1HbC_ahxV0cmU6m4dk4PYDgCuGVPoYc
            return token;
        }else {
            return "fail";
        }
    }
    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        //解密token得到用户信息
        //拿到token
        String token = request.getParameter("token");
        //两种取盐的方式,可以从服务器去取也可以从url中取
//        String salt = request.getHeader("X-forwarded-for");
        String salt = request.getParameter("salt");
        //查看方法需要参数ctrl+alt+/
        //解密
        Map<String, Object> map = JwtUtil.decode(token, key, salt);
        if (map!=null && map.size()>0){
            String userId = (String) map.get("userId");
            //获取数据的过程,写成服务
            UserInfo userInfo=userService.verify(userId);
            if (userInfo!=null){
                return "success";
            }else {
                return "fail";
            }
        }
        return "fail";
    }
}
