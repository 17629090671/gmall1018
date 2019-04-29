package com.atguigu.gmall1018.manager.mapper;

import com.atguigu.gmall1018.bean.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {

    // 根据三级分类Id 查询平台属性对象集合
    List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(Long catalog3Id);

    //通过平台属性值id进行查询
    List<BaseAttrInfo> selectAttrInfoListByIds(@Param("valueIds") String valueIds);
}



