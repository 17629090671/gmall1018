package com.atguigu.gmall1018.manager.controller;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author enlong zhang
 * @date 2019/4/16 - 13:00
 */
@RestController
@CrossOrigin
public class FileUploadController {

    // ip  http://192.168.67.215
    // 获取文件中的地址
    @Value("${fileServer.url}")
    private String fileUrl;
    /*
        springmvc 如何做文件上传，做文件上传使用的是哪个对象？
     */

    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file)  throws IOException, MyException {
        // 最终生成的图片全路径
        String imgUrl = fileUrl;

        if (file!=null){
            // 读取配置文件 得到 文件服务器的地址
            String configFile  = this.getClass().getResource("/tracker.conf").getFile();
            // 初始化
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getConnection();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            // 得到上传文件名称
            // String orginalFilename="D://img//zly.jpg";
            String originalFilename = file.getOriginalFilename();
            // 获取文件的后缀名 zly.jpg
            String extName = StringUtils.substringAfterLast(originalFilename,".");
            System.out.println("文件后缀名："+extName);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);

            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                System.out.println("s = " + path);
                //			s = group1
                //			s = M00/00/00/wKhD11yxgMGARWm9AACGx2c4tJ4283.jpg
                imgUrl+="/"+path;
            }
        }
        // "http://192.168.67.215/group1/M00/00/00/wKhD11yxgMGARWm9AACGx2c4tJ4283.jpg";
        // fileUrl/group1/
        return imgUrl;
    }

}
