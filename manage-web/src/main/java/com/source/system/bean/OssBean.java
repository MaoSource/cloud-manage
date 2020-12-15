package com.source.system.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/10/10:42
 * @Description:
 */
@Data
//交给spring管理
@Component
//获取配置文件中的属性，只需要写个属性头数据将会自动获取
@ConfigurationProperties(prefix = "alioss")
public class OssBean {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
