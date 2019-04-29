package com.atguigu.gmall1018.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall1018.config.RedisUtil;
import com.atguigu.gmall1018.bean.*;
import com.atguigu.gmall1018.manager.constant.ManageConst;
import com.atguigu.gmall1018.manager.mapper.*;
import com.atguigu.gmall1018.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {


    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;




    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        // select * from catalog2 from where catalogid = ?
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return   baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    // 查询所有平台属性
    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
//        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
//        baseAttrInfo.setCatalog3Id(catalog3Id);
//        return baseAttrInfoMapper.select(baseAttrInfo);

        return baseAttrInfoMapper.getBaseAttrInfoListByCatalog3Id(Long.parseLong(catalog3Id));
    }

    /**
     *  新增，修改 都在同一个方法中完成！
     * @param baseAttrInfo
     */
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        // 操作BaseAttrInfo
        if (baseAttrInfo.getId()!=null && baseAttrInfo.getId().length()>0){
            // 做修改
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            // 对应调用mapper对象进行新增
            baseAttrInfo.setId(null); // 使id 实现自增
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        // 对属性值的操作BaseAttrValue
        // 无论是新增，还是修改，先将BaseAttrValue 中 的数据删除 {baseAttrValue.attrId==baseAttrInfo.id}
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        // 添加数据BaseAttrValue
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList!=null && attrValueList.size()>0){
            // 循环遍历
            for (BaseAttrValue attrValue : attrValueList) {
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

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll() ;
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        // 操作spuInfo对象

        // 根据业务需求，可以将添加，或者是编辑放在一起写。
        if (spuInfo.getId()==null || spuInfo.getId().length()==0){
            // 添加业务
            spuInfo.setId(null);
            spuInfoMapper.insertSelective(spuInfo);
        }else{
            // 修改业务
            spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
        }

        // 操作spuImage
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (spuImageList!=null && spuImageList.size()>0){
            for (SpuImage spuImage : spuImageList) {
                spuImage.setId(null);
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);

            }
        }

        // 操作spuSaleAttr spuSaleAttrValue
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if (spuSaleAttrList!=null && spuSaleAttrList.size()>0){
            // 循环遍历添加数据
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setId(null);
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                
                // 添加销售属性值
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setId(null);
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }


    }

    @Override
    public List<SpuImage> getSpuImageList(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }


    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
        // 调用mapper 层.
        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        // 保存skuInfo
        if (skuInfo.getId()!=null && skuInfo.getId().length()>0){
            // 编辑
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }else{
            // 新增
            skuInfo.setId(null);
            skuInfoMapper.insertSelective(skuInfo);
        }
        // 保存skuImage
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (skuImageList!=null && skuImageList.size()>0){
            // 循环遍历
            for (SkuImage skuImage : skuImageList) {
                skuImage.setId(null);
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(skuImage);
            }
        }
        // 字符串长度 str.length();，数组长度 length，集合长度 size();文件长度：length()
        // 保存skuAttrValue
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if (skuAttrValueList!=null && skuAttrValueList.size()>0){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setId(null);
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insertSelective(skuAttrValue);
            }
        }
        // 保存skuSaleAttrValue
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if (skuSaleAttrValueList!=null &&skuSaleAttrValueList.size()>0){
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setId(null);
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }
    }

    @Override
    public SkuInfo getSkuInfo(String skuId) {
        try {
            // 获取jedis 客户端
            Jedis jedis = redisUtil.getJedis();
            // 定义key
            String skuKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            // 查询缓存数据
            String skuJson = jedis.get(skuKey);
            // 此时缓存中没有数据
            if (skuJson==null || skuJson.length()==0){
                System.out.println("缓存中没有数据！");
                // 定义一个分布式锁的Key
                String skuLockKey=ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKULOCK_SUFFIX;
                // 执行完返回的结果
                String LockKey = jedis.set(skuLockKey, "OK", "NX", "PX", ManageConst.SKULOCK_EXPIRE_PX);
                if ("OK".equals(LockKey)){
                    // 设置分布式锁！
                    System.out.println("获取分布式锁！");
                    // 从数据库中获取数据
                    SkuInfo skuInfoDB = getSkuInfoDB(skuId);
                    // 将数据放入redis 中
                    // jedis.set(skuKey,JSON.toJSONString(skuInfoDB));
                    // 带过期时间的
                    jedis.setex(skuKey,ManageConst.SKUKEY_TIMEOUT,JSON.toJSONString(skuInfoDB));
                    return skuInfoDB;
                }else {
                    // 其他人应该等待
                    Thread.sleep(1000);
                    // 继续查询数据
                    return getSkuInfo(skuId);
                }
            }else{
                // 有缓存
                SkuInfo skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
                return skuInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return getSkuInfoDB(skuId);
    }

    /**
     *  从数据库中获取数据
     *  ctrl+alt+m
     * @param skuId
     * @return
     */
    private SkuInfo getSkuInfoDB(String skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);

        // select * from skuImage where skuId = ? {skuId}
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuInfo.getId());
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);

        // 然后将查询后的结果赋值给skuInfo 对象
        skuInfo.setSkuImageList(skuImageList);

        // 平台属性值Id
        // select * from skuAttrValue where skuId = ? {skuId}
        SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuId);
        List<SkuAttrValue> attrValueList = skuAttrValueMapper.select(skuAttrValue);
        skuInfo.setSkuAttrValueList(attrValueList);

        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {
        // 参数一定不能写反！
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(),skuInfo.getSpuId());
    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
        // 调用mapper 层
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(List<String> attrValueIdList) {
        //attrValueIdList
        String valueId = StringUtils.join(attrValueIdList.toArray(),",");
        System.out.println("valueId"+valueId);
        List<BaseAttrInfo> baseAttrInfoList= baseAttrInfoMapper.selectAttrInfoListByIds(valueId);
        return baseAttrInfoList;
    }
}
