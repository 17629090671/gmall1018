package com.atguigu.gmall1018.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 16:20
 */
@Data
public class BaseCatalog2 implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String catalog1Id;
}