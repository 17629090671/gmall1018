package com.atguigu.gmall1018.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall1018.bean.BaseSaleAttr;
import com.atguigu.gmall1018.bean.SpuInfo;
import com.atguigu.gmall1018.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/16 - 1:22
 */
@Controller
@CrossOrigin
public class SpuManageController {
    @Reference
    private ManagerService managerService;

    //    @RequestMapping("spuList")
//    @ResponseBody
//    public List<SpuInfo> spuList(String catalog3Id){
//        // http://localhost:8082/spuList?catalog3Id=61
//        SpuInfo spuInfo = new SpuInfo();
//        spuInfo.setCatalog3Id(catalog3Id);
//        return managerService.getSpuInfoList(spuInfo);
//    }
    @RequestMapping("spuList")
    @ResponseBody
    public List<SpuInfo> spuList(SpuInfo spuInfo) {
        // http://localhost:8082/spuList?catalog3Id=61
        //        SpuInfo spuInfo = new SpuInfo();
        //        spuInfo.setCatalog3Id(catalog3Id);
        return managerService.getSpuInfoList(spuInfo);
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){
        return managerService.getBaseSaleAttrList();
    }
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public void saveSpuInfo(@RequestBody SpuInfo spuInfo){
        //调用业务层完成添加
        managerService.saveSpuInfo(spuInfo);
    }

}
