package com.atguigu.gmall1018.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author enlong zhang
 * @date 2019/4/27 - 17:47
 */
public class CartInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;
    @Column
    String userId;
    @Column
    String skuId;
    @Column
    BigDecimal cartPrice;
    @Column
    Integer skuNum;
    @Column
    String imgUrl;
    @Column
    String skuName;

    // 实时价格
    @Transient
    BigDecimal skuPrice;
    // 下订单的时候，商品是否勾选
    @Transient
    String isChecked="0";
}