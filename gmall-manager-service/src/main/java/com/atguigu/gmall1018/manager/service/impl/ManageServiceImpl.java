package com.atguigu.gmall1018.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall1018.bean.*;
import com.atguigu.gmall1018.manager.mapper.*;
import com.atguigu.gmall1018.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 18:54
 */
@Service//com.alibaba.dubbo.config.annotation.Service;
public class ManageServiceImpl implements ManagerService {
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);
    }

    /**
     * 修改,新增在同一个方法内执行
     * @param baseAttrInfo
     */
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //操作BaseAttrInfo
        if(baseAttrInfo.getId()!=null && baseAttrInfo.getId().length()>0){
            //做修改
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else {
            //对应调用mapper对象进行更新
            baseAttrInfo.setId(null);
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }
        //对属性值的操作BaseAttrValue
        //无论是新增还是修改,先将BaseAttrValue 中 的数据删除 {baseAttrValue.attrId==baseAttrInfo.id}
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);
        //添加数据BaseAttrValue
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList!=null && attrValueList.size()>0){
            for (BaseAttrValue attrValue : attrValueList){
                attrValue.setId(null);
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        // 根据attrId 获取平台属性对象
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        // 通过attrId 查询到平台属性值集合
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
        // 给平台属性值集合赋值
        baseAttrInfo.setAttrValueList(baseAttrValues);

        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        return null;
    }
}
