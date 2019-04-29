package com.atguigu.gmall1018.manager.mapper;

import com.atguigu.gmall1018.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
    // 通过spuId 查询销售属性值列表并拼接map
    List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);
}
