package com.atguigu.gmall1018.service;

import com.atguigu.gmall1018.bean.UserInfo;
import com.atguigu.gmall1018.bean.UserAddress;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/9 - 19:51
 */
public interface UserService {
    //查询所有
    List<UserInfo> findAll();

    List<UserAddress> findAddressByUserId(String userId);

    //登录的方法
    UserInfo login(UserInfo userInfo);

    //用户信息验证功能
    UserInfo verify(String userId);
}
