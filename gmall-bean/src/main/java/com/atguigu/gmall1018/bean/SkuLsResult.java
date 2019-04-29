package com.atguigu.gmall1018.bean;

/**
 * @author enlong zhang
 * @date 2019/4/22 - 19:15
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuLsResult implements Serializable {

    List<SkuLsInfo> skuLsInfoList;

    long total;

    long totalPages;

    List<String> attrValueIdList;
}
