package com.source.system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.source.system.bean.OssBean;
import com.source.system.service.AliOssService;
import com.source.utils.Files;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/10/11:06
 * @Description:
 */
@Service
@Slf4j
public class AliOssServiceImpl implements AliOssService, InitializingBean {

    @Autowired
    private OssBean bean;

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    @Override
    public void createBucket() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Override
    public String upload(MultipartFile file) {

        log.info("file" + file);

        String fileUrl = "";

        try {
//            创建OSSClient实例
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            判断bucketName是否存在
            if (!oss.doesBucketExist(bucketName)){
//                如不存在就创建
                oss.createBucket(bucketName);
//                设置bucketName属性
                oss.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

//            获取上传文件流
            InputStream inputStream = file.getInputStream();

//            构造文件夹路劲   avatar/yyyy/MM/dd/fileName
            String s = new DateTime().toString("yyyy/MM/dd");

//            获取文件名后缀
            String originalFilename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            Files files = new Files();

            String files1 = files.files(originalFilename.substring(1));

            log.info("file" + files1);

//            文件名使用UUID生成
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            ObjectMetadata objectMetadata = new ObjectMetadata();
            //设置公共读权限
//            objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
            objectMetadata.setContentType(files.getContentType(originalFilename));

//            拼接文件名称
            String fileName =  s + "/" + files1 + "-" + uuid + originalFilename;

            oss.putObject(bucketName, fileName, inputStream, objectMetadata);

            // 关闭OSSClient。
            oss.shutdown();

            //默认十年不过期
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);

            //bucket名称  文件名   过期时间
            fileUrl = oss.generatePresignedUrl(bucketName, fileName, expiration).toString();

//            return "https://managers.oss-cn-shenzhen.aliyuncs.com/" + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }

        return fileUrl.substring(0, fileUrl.indexOf("?"));
    }

    @Override
    public void download(String name) throws IOException {
        // <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, name);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Override
    public void listFile() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }
        // 关闭OSSClient。
        ossClient.shutdown();

    }

    @Override
    public void deleteFile(String fileName) {
        // <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        endpoint = bean.getEndpoint();

        accessKeyId = bean.getAccessKeyId();

        accessKeySecret = bean.getAccessKeySecret();

        bucketName = bean.getBucketName();
    }
}
