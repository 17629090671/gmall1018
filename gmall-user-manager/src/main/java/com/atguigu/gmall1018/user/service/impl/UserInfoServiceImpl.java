package com.atguigu.gmall1018.user.service.impl;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall1018.config.RedisUtil;
import com.atguigu.gmall1018.bean.UserAddress;
import com.atguigu.gmall1018.bean.UserInfo;
import com.atguigu.gmall1018.service.UserService;
import com.atguigu.gmall1018.user.mapper.UserAddressMapper;
import com.atguigu.gmall1018.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

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
    @Autowired
    private RedisUtil redisUtil;

    public String userKey_prefix = "user:";
    public String userinfoKey_suffix = ":info";
    public int userKey_timeOut = 60 * 60 * 24;

    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectAll();
    }

    @Override
    public List<UserAddress> findAddressByUserId(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        // 需要对密码进行加密
        String passwd = userInfo.getPasswd();
        // 202cb962ac59075b964b07152d234b70  实际密码为123
        String newPassword = DigestUtils.md5DigestAsHex(passwd.getBytes());
        userInfo.setPasswd(newPassword);
        // 用户名，密码查询出唯一用户！
        UserInfo info = userInfoMapper.selectOne(userInfo);

        // 将用户信息放入缓存
        if (info != null) {
            Jedis jedis = redisUtil.getJedis();

            // 定义key  商品sku:skuId:info
            // userKey user:userId:info
            String userKey = userKey_prefix + info.getId() + userinfoKey_suffix;

            // 保存数据，设计过期时间
            jedis.setex(userKey, userKey_timeOut, JSON.toJSONString(info));

            jedis.close();
            return info;
        }
        return info;
    }

    @Override
    public UserInfo verify(String userId) {
        Jedis jedis = redisUtil.getJedis();
        //拼接key:  user:userId:info 由三部分组成
        String key = userKey_prefix + userId + userinfoKey_suffix;
        String userJson = jedis.get(key);
        if (StringUtils.isNotEmpty(userJson)) {
            //将userJson转换为字符串
            UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);

            if (userInfo != null) {
                //延长有效时间
                jedis.setex(key, userKey_timeOut, JSON.toJSONString(userInfo));
                return userInfo;
            }
        }
        return null;
    }
}
