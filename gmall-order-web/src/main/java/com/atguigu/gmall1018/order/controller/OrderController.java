package com.atguigu.gmall1018.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall1018.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/11 - 22:35
 */
@Controller
public class OrderController {
    //@Autowired
    @Reference
    private UserService userService;
    @RequestMapping("trade")
    @ResponseBody
    public List trade(String userId){
        //调用服务 查询用户地址列表
        return userService.findAddressByUserId(userId);
    }

}
