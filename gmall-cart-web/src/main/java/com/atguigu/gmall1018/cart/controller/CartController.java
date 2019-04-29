package com.atguigu.gmall1018.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author enlong zhang
 * @date 2019/4/27 - 8:15
 */
@Controller
public class CartController {
    //添加购物车控制器
    @RequestMapping("addToCart")
    public String addToCart() {
        return "success";
    }

}
