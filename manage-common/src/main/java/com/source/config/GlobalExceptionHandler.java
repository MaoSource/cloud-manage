package com.source.config;

import com.source.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/08/14:44
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
//        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e){
//        e.printStackTrace();
        log.error(e.getErrMsg());
        return Result.error().code(e.getCode())
                .message(e.getErrMsg());
    }


}
