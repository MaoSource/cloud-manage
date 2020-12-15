package com.source.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/10/11:01
 * @Description:
 */
public interface AliOssService {

//    创建
    void createBucket();

//    上传

    String upload(MultipartFile file);

//    下载
    void download(String name) throws IOException;

//    列举
    void listFile();

//    删除
    void deleteFile(String fileName);
}
