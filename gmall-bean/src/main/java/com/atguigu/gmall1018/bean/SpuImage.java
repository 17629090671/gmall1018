package com.atguigu.gmall1018.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * @author enlong zhang
 * @date 2019/4/16 - 17:23
 */
//商品图片实体类
@Data
public class SpuImage  implements Serializable {
    @Column
    @Id
    private String id;
    @Column
    private String spuId;
    @Column
    private String imgName;
    @Column
    private String imgUrl;
}