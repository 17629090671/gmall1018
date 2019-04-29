package com.atguigu.gmall1018.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author enlong zhang
 * @date 2019/4/22 - 19:09
 */
@Data
public class SkuLsParams implements Serializable {

    private String keyword;

    private String catalog3Id;

    private String[] valueId;
    //用于分页的参数
    private int pageNo = 1;

    private int pageSize = 20;
}
