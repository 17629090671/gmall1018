package com.atguigu.gmall1018.user.controller;

import com.atguigu.gmall1018.bean.UserInfo;
import com.atguigu.gmall1018.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/11 - 21:56
 */
@Controller
public class UserInfoController {
    @Autowired
    private UserService userService;
    @RequestMapping("findAll")
    @ResponseBody
    public List<UserInfo> findAll(){
        return userService.findAll();
    }
}
