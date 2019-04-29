package com.atguigu.gmall1018.bean;

/**
 * @author enlong zhang
 * @date 2019/4/22 - 7:21
 */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuLsInfo implements Serializable {

    String id;

    BigDecimal price;

    String skuName;

    String skuDesc;

    String catalog3Id;

    String skuDefaultImg;

    //热度排名
    Long hotScore = 0L;

    //平台属性值集合
    List<SkuLsAttrValue> skuAttrValueList;
}
