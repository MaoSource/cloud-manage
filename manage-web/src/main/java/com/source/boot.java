package com.source;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/07/20:45
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.source.system.mapper")
//开启swaggerUI2注解
@EnableSwagger2
public class boot {
    public static void main(String[] args) {
        SpringApplication.run(boot.class);
    }
}
