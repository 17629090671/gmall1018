package com.atguigu.gmall1018.service;

import com.atguigu.gmall1018.bean.*;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 18:45
 */
public interface ManagerService {
    //查询所有一级分类的数据
    public List<BaseCatalog1> getCatalog1();

    //根据一级分类id查询所有二级分类数据
    public List<BaseCatalog2> getCatalog2(String catalog1Id);

    //根据二级分类id查询所有三级分类数据
    public List<BaseCatalog3> getCatalog3(String catalog2Id);

    //根据三级分类id查询平台属性基本属性信息
    public List<BaseAttrInfo> getAttrList(String catalog3Id);

    //保存属性的方法
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //通过平台属性id获取平台属性值
    List<BaseAttrValue> getAttrValueList(String attrId);
}
