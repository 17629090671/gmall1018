package com.atguigu.gmall1018.user.service.impl;




import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall1018.bean.UserAddress;
import com.atguigu.gmall1018.bean.UserInfo;
import com.atguigu.gmall1018.service.UserService;
import com.atguigu.gmall1018.user.mapper.UserAddressMapper;
import com.atguigu.gmall1018.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/11 - 21:52
 */
@Service
public class UserInfoServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectAll();
    }
    @Override
    public List<UserAddress> findAddressByUserId(String userId){
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }
}
