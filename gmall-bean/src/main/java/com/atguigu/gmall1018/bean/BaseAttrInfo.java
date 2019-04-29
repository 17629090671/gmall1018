package com.atguigu.gmall1018.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 16:22
 */
@Data
public class BaseAttrInfo implements Serializable {
    //数据库中实际存在的字段
    @Id
    @Column
    //获取 mysql数据库主键自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;

    //@Transient业务所需要的字段,数据库中实际不存在
    @Transient
    private List<BaseAttrValue> attrValueList;

}
