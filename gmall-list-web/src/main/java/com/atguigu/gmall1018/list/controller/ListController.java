package com.atguigu.gmall1018.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall1018.bean.*;
import com.atguigu.gmall1018.service.ManagerService;
import com.atguigu.gmall1018.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ListController {



    @Reference
    private ListService listService;

    @Reference
    private ManagerService managerService;

    @RequestMapping("list.html")
//    @ResponseBody
    public String list(SkuLsParams skuLsParams, HttpServletRequest request){
        // 设置pageSize 大小
        skuLsParams.setPageSize(2);

        // 返回的结果集
        SkuLsResult skuLsResult = listService.search(skuLsParams);

        String string = JSON.toJSONString(skuLsResult);


        // 将结果集中的商品信息显示到list.html
        List<SkuLsInfo> skuLsInfoList = skuLsResult.getSkuLsInfoList();

        // 平台属性平台属性值集合

        // 获取平台属性值Id
        List<String> attrValueIdList = skuLsResult.getAttrValueIdList();
        //  SELECT * FROM base_attr_info bai INNER JOIN base_attr_value bav ON bai.id = bav.attr_id WHERE bav.id in (14,81,82,83)
        List<BaseAttrInfo> baseAttrInfoList = managerService.getAttrList(attrValueIdList);

        //  声明一个字符串 来保存条件拼接的url
        String urlParam = makeUrlParam(skuLsParams);

        // 声明一个存储面包屑的集合
        ArrayList<BaseAttrValue> baseAttrValueArrayList = new ArrayList<>();
        // baseAttrInfoList 显示到页面的数据 ，包含了平台属性、平台属性值的名称 ，
        // 如果urlParams 中的valueId 与 BaseAttrValue.id 相等 则将 baseAttrInfoList 中的 attrValueList 属性的BaseAttrValue 对象移除
        //  使用迭代器循环集合，才能将数据删除。
        for (Iterator<BaseAttrInfo> iterator = baseAttrInfoList.iterator(); iterator.hasNext(); ) {
            BaseAttrInfo baseAttrInfo =  iterator.next();
            // 获取平台属性值的集合
            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
            // 循环平台属性值集合对象
            for (BaseAttrValue baseAttrValue : attrValueList) {
                // 判断url 参数中的vlaueId不能为空
                if (skuLsParams.getValueId()!=null && skuLsParams.getValueId().length>0){
                    // 循环valueId
                    for (String valueId : skuLsParams.getValueId()) {
                        if (valueId.equals(baseAttrValue.getId())){
                            // valueId 如果相等，则将集合中的数据进行删除
                            iterator.remove();
                            //  平台属性名：平台属性值名
                            //  给平台属性值的对象的Name 修改为：平台属性名：平台属性值名

                            // 创建一个平台属性值对象
                            BaseAttrValue baseAttrValueed = new BaseAttrValue();
                            baseAttrValueed.setValueName(baseAttrInfo.getAttrName()+": " +baseAttrValue.getValueName());

                            // 重构urlParam 参数条件
                            // 此时 urlParams 应该是最新的urlparam ，应该把urlparam 保存.
                            String urlParams = makeUrlParam(skuLsParams, valueId);
                            // 保存最新的urlparam参数

                            baseAttrValueed.setUrlParam(urlParams);

                            // 将构造好的平台属性值对象放入集合
                            baseAttrValueArrayList.add(baseAttrValueed);


                        }
                    }
                }
            }
        }

        // 保存关键字
        request.setAttribute("keyword",skuLsParams.getKeyword());

        // 保存到作用域
        request.setAttribute("baseAttrValueArrayList",baseAttrValueArrayList);

        // 保存urlParam
        request.setAttribute("urlParam",urlParam);
        // 保存数据进行回显
        request.setAttribute("baseAttrInfoList",baseAttrInfoList);
        // 将skuLsInfoList 保存到作用域中。
        request.setAttribute("skuLsInfoList",skuLsInfoList);
        // 制作一个分页
        request.setAttribute("totalPage",skuLsResult.getTotalPages());
        request.setAttribute("pageNo",skuLsParams.getPageNo());

        return "list";
    }

    // 制作url后面的参数

    /**
     *
     * @param skuLsParams url 的参数对象
     * @param excludeValueIds 面包屑的valueId
     * @return
     */
    private String makeUrlParam(SkuLsParams skuLsParams,String... excludeValueIds) {

        String urlParam = "";
        //总的条件 http://localhost:8086/list.html?keyword=小米&catalog3Id=61&valueId=83&valueId=82

        // http://localhost:8086/list.html?keyword=小米
        if (skuLsParams.getKeyword()!=null && skuLsParams.getKeyword().length()>0){
            urlParam+="keyword="+skuLsParams.getKeyword();
        }
        //  拼接三级分类Id
        if (skuLsParams.getCatalog3Id()!=null && skuLsParams.getCatalog3Id().length()>0){
            // 什么时候追加&
            if (urlParam.length()>0){
                urlParam+="&";
            }
            urlParam+="catalog3Id="+skuLsParams.getCatalog3Id();
        }

        // 拼接平台属性值Id
        if (skuLsParams.getValueId()!=null && skuLsParams.getValueId().length>0){
            // 循环遍历
            for (String valueId : skuLsParams.getValueId()) {
                if (excludeValueIds!=null && excludeValueIds.length>0){
                    // 获取valueId
                    String excludeValueId = excludeValueIds[0];
                    if (valueId.equals(excludeValueId)){

                        // 如果valueId 相等，则不应该拼接参数，
                        // break or continue; 选中continue！
                        continue;
                    }

                }
                // 什么时候追加&
                if (urlParam.length()>0){
                    urlParam+="&";
                }
                urlParam+="valueId="+valueId;
            }
        }
        return urlParam;
    }


}
