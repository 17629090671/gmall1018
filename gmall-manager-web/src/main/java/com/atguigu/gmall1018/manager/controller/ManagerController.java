package com.atguigu.gmall1018.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall1018.bean.*;
import com.atguigu.gmall1018.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author enlong zhang
 * @date 2019/4/12 - 19:16
 */
@Controller
@CrossOrigin
public class ManagerController {
    @Reference
    private ManagerService managerService;

    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<BaseCatalog1> getCatalog1(){
        return managerService.getCatalog1();
    }
    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        return managerService.getCatalog2(catalog1Id);
    }
    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        return managerService.getCatalog3(catalog2Id);
    }
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<BaseCatalog3> attrInfoList(String catalog3Id){
        return managerService.getCatalog3(catalog3Id);
    }
    //保存的方法
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        managerService.saveAttrInfo(baseAttrInfo);
    }
    //通过属性id查询到平台属性值集合!
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){
        return managerService.getAttrValueList(attrId);
    }
}
