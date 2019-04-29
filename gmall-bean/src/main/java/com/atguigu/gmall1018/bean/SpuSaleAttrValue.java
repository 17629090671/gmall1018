package com.atguigu.gmall1018.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author enlong zhang
 * @date 2019/4/16 - 17:28
 */
//销售属性值表
@Data
public class SpuSaleAttrValue implements Serializable {

    @Id
    @Column
    String id;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;
    //判断当前属性值是否被选中
    @Transient
    String isChecked;
}