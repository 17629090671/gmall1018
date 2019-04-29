package com.atguigu.gmall1018.item.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall1018.bean.SkuInfo;
import com.atguigu.gmall1018.bean.SkuSaleAttrValue;
import com.atguigu.gmall1018.bean.SpuSaleAttr;
import com.atguigu.gmall1018.config.LoginRequire;
import com.atguigu.gmall1018.service.ListService;
import com.atguigu.gmall1018.service.ManagerService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/19 - 17:51
 */
@Controller
public class ItemController {

    @Reference
    private ManagerService managerService;
    @Reference
    private ListService listService;
    //访问商品详情
    @RequestMapping("{skuId}.html")

    //@LoginRequire(autoRedirect = true)

    public String item(@PathVariable String skuId, HttpServletRequest request){
        System.out.println("商品ID："+skuId);
        // 根据skuId 查询数据库
        // select * from skuInfo  where id = skuId
        SkuInfo skuInfo = managerService.getSkuInfo(skuId);

        // 调用服务层获取销售属性数据集合
        List<SpuSaleAttr> spuSaleAttrList = managerService.getSpuSaleAttrListCheckBySku(skuInfo);

        // 获取销售属性值集合，以及skuId
        List<SkuSaleAttrValue> skuSaleAttrValueListBySpu = managerService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());
        //        144	39	黑色
        //        147	39	4G+64G
        //        145	40	金色
        //        147	40	4G+64G

        /*
            生成map
            map.put("144|147",39)
			map.put("145|147",40)
          */
        // 声明一个集合
        HashMap<String, Object> map = new HashMap<>();
        String key = "";
        // 循环集合数据
        for (int i = 0; i < skuSaleAttrValueListBySpu.size(); i++) {
            // 获取单行数据
            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueListBySpu.get(i);
            // 什么时候拼接|
            if (key.length()>0){
                key+="|";
            }
            // key = 144
            // key = 144|
            // key = 144|147
            // key = "";
            key+=skuSaleAttrValue.getSaleAttrValueId();
            // 当循环的skuId与下次循环的skuId 不相等的时候，
            // 当循环的下标到最后的时候，结束循环，将数据添加到map 中
            if ( skuSaleAttrValueListBySpu.size()==(i+1) || !skuSaleAttrValue.getSkuId().equals( skuSaleAttrValueListBySpu.get(i+1).getSkuId())){
                map.put(key,skuSaleAttrValue.getSkuId());
                // 将key 置空
                key = "";
            }
        }

        // 将map 封装成json 对象保存到页面中。

        String valuesSkuJson  = JSON.toJSONString(map);
        System.out.println(valuesSkuJson);

        // 将其放入到作用域中
        request.setAttribute("valuesSkuJson",valuesSkuJson);

        // 保存skuInfo 对象
        request.setAttribute("skuInfo",skuInfo);
        // 保存SpuSaleAttr 对象
        request.setAttribute("spuSaleAttrList",spuSaleAttrList);

        //调用热度排名
        listService.incrHotScore(skuId);
        return "item";
    }
}