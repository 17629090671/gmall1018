package com.atguigu.gmall1018.service;

import com.atguigu.gmall1018.bean.UserAddress;
import com.atguigu.gmall1018.bean.UserInfo;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/9 - 19:51
 */
public interface UserService {
    //查询所有
    List<UserInfo> findAll();

    List<UserAddress> findAddressByUserId(String userId);
}
