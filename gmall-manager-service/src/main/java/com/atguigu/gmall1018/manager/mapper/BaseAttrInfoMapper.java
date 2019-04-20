package com.atguigu.gmall1018.manager.mapper;


import com.atguigu.gmall1018.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 16:35
 */
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {
    // 根据三级分类id查询属性表
    List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(Long catalog3Id);
}
