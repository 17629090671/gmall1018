package com.atguigu.gmall1018.manager.mapper;

import com.atguigu.gmall1018.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    // 根据spuId 查询销售属性对象集合
    List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);
    // 通过spuId，skuId 查询销售属性，属性值，锁定
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(String skuId, String spuId);
}
