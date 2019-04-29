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

    // 通过平台属性Id 查询平台属性值集合
    List<BaseAttrValue> getAttrValueList(String attrId);

    // 通过属性id 查询平台属性对象
    BaseAttrInfo getAttrInfo(String attrId);

    //构造了一个spu对象查询
    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);

    //只能根据分类id查询
    List<SpuInfo> getSpuInfoList(String catalog3Id);

    // 查询基本销售属性表
    List<BaseSaleAttr> getBaseSaleAttrList();

    //保存商品spu信息
    void saveSpuInfo(SpuInfo spuInfo);

    //根据spuId查询spuImage列表
    List<SpuImage> getSpuImageList(String spuId);

    //根据spuId查询销售属性对象
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    //保存skuInfo数据
    void saveSkuInfo(SkuInfo skuInfo);

    //根据skuId获得skuInfo对象
    SkuInfo getSkuInfo(String skuId);

    //根据spuId,skuId查询销售属性
    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    // 根据spuId 查询销售属性值的集合
    List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId);

    //通过平台属性值id进行查询
    List<BaseAttrInfo> getAttrList(List<String> attrValueIdList);
}