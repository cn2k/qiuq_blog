package com.kabu.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class QiniuUtils {

    //第一种方法自定义域名
//    public static  final String url = "http://qiniu.7777666.xyz";
    //此处需要修改你的七牛云储存url地址
    public static  final String url = "https://qiniu1.7777666.xyz";

    @Value("${qiniu.accessKey}")
    private  String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private  String accessSecretKey;

    public  boolean upload(MultipartFile file,String fileName){

        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Region.xinjiapo());
        //修改云存储地域
        Configuration cfg = new Configuration(Region.beimei());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，空间名称，然后准备上传
        String bucket = "cloudflare1";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return false;
    }
}
