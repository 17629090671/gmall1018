package com.atguigu.gmall1018.service;

import com.atguigu.gmall1018.bean.SkuLsResult;
import com.atguigu.gmall1018.bean.SkuLsInfo;
import com.atguigu.gmall1018.bean.SkuLsParams;

/**
 * @author enlong zhang
 * @date 2019/4/22 - 8:14
 */
public interface ListService {
    //保存数据到es中
    public void saveSkuLsInfo(SkuLsInfo skuLsInfo);

    //根据用户的输入从参数中查询数据
    SkuLsResult search(SkuLsParams skuLsParams);

    //热度排名
    void incrHotScore(String skuId);
}
