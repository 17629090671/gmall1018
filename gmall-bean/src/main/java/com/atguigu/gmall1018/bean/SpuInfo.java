package com.atguigu.gmall1018.bean;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/16 - 1:06
 */
@Data
public class SpuInfo implements Serializable {
    @Column
    @Id
    //获取到mysql数据库中表中主键自增的值!
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String spuName;

    @Column
    private String description;

    @Column
    private  String catalog3Id;

    @Transient
    private List<SpuSaleAttr> spuSaleAttrList;
    @Transient
    private List<SpuImage> spuImageList;
}
