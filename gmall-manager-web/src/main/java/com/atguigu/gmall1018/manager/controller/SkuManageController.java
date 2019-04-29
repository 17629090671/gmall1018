package com.atguigu.gmall1018.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall1018.bean.SkuInfo;
import com.atguigu.gmall1018.bean.SkuLsInfo;
import com.atguigu.gmall1018.bean.SpuImage;
import com.atguigu.gmall1018.bean.SpuSaleAttr;
import com.atguigu.gmall1018.service.ListService;
import com.atguigu.gmall1018.service.ManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/17 - 17:51
 */
@Controller
@CrossOrigin
public class SkuManageController {
    @Reference
    private ManagerService managerService;
    @Reference
    private ListService listService;
    @RequestMapping("spuImageList")
    @ResponseBody
    public List<SpuImage> spuImageList(String spuId){
        return   managerService.getSpuImageList(spuId);
    }

    //根据spuId查询销售属性对象
    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){
        return managerService.getSpuSaleAttrList(spuId);
    }

    //保存skuInfo数据
    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
        managerService.saveSkuInfo(skuInfo);
        return "ok!";
    }

    //
    @RequestMapping("onSale")
    @ResponseBody
    public String onSale(String skuId){
        //创建SkuLsInfo
        //skuLsInfo是空的,没有数据
        SkuLsInfo skuLsInfo = new SkuLsInfo();
        SkuInfo skuInfo = managerService.getSkuInfo(skuId);
        //把skuInfo中的数据拷贝到skuId,前面是源数据后面是目标数据

        BeanUtils.copyProperties(skuInfo,skuLsInfo);

        listService.saveSkuLsInfo(skuLsInfo);
        return "ok!";
    }
}
