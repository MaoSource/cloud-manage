package com.source.system.controller;

import com.source.response.Result;
import com.source.system.service.AliOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/10/19:04
 * @Description:
 */
@Api(value = "文件上传模块", tags = "文件上传接口")
@RestController
@RequestMapping("/upload")
@CrossOrigin
@Slf4j
public class UploadFileController {

    @Autowired
    private AliOssService aliOssService;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "文件上传", notes = "头像上传")
    public Result findUser(@RequestPart("file") MultipartFile file){
        log.info("fileName:" + file);
        String upload = aliOssService.upload(file);
        return Result.ok().data("file",upload);
    }
    @PostMapping("/deleteFile")
    @ApiOperation(value = "文件删除", notes = "删除文件")
    public Result deleteFile(String file){
        try {
            String[] split = file.split(".com/");
            log.info("文件名：" + split[1]);
            aliOssService.deleteFile(split[1]);
            return Result.ok().data("message","成功");
        }catch (Exception e){
            log.info("删除文件出错！");
            return Result.error();
        }

    }


}
