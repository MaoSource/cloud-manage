package com.source.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.source.config.BusinessException;
import com.source.response.Result;
import com.source.response.ResultCode;
import com.source.system.bean.TbUser;
import com.source.system.service.AliOssService;
import com.source.system.service.TbUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/system/tb-user")
//@CrossOrigin    //解决跨域
@Api(value = "用户管理模块", tags = "用户管理接口")
@Slf4j
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private AliOssService aliOssService;

    @GetMapping("/")
    @ApiOperation(value = "用户分页列表", notes = "查询所有用户分页信息")
    public Result pageList(@RequestParam(required = true,defaultValue = "1") Integer current,
                           @RequestParam(required = true,defaultValue = "7") Integer size){
        Page<TbUser> objectPage = new Page<>(current, size);
        IPage<TbUser> page = tbUserService.page(objectPage);
        return Result.ok().data("page",page);
    }

    @GetMapping("/list")
    @ApiOperation(value = "用户分页列表", notes = "根据筛选查询所有用户分页信息")
    public Result List(@RequestParam(required = true,defaultValue = "1") Integer current,
                       @RequestParam(required = true,defaultValue = "7") Integer size,
                       String username, String nickname, String email, Integer sex, Long departmentId, String startDate, String endDate){
        Page<TbUser> page = new Page<>(current, size);
        IPage<TbUser> list = tbUserService.findUserPage(page, username, nickname, email, sex, departmentId, startDate, endDate);
        return Result.ok().data("list",list);
    }

    @GetMapping("/findUser")
    @ApiOperation(value = "用户列表", notes = "查询所有用户信息")
    public Result findUser(){
        List<TbUser> list = tbUserService.list();
        return Result.ok().data("user",list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户ID查询", notes = "查询单个用户信息")
    public Result findById(@PathVariable("id") Long id) throws BusinessException {
            TbUser byId = tbUserService.getById(id);
            if (byId != null){
                return Result.ok().data("user",byId);
            }else {
                throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
            }
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加用户信息")
    public Result addUser(@RequestBody TbUser user){
        log.info("user" + user.toString());
        try {
            tbUserService.addUser(user);
            return Result.ok();
        }catch (BusinessException e){
            return Result.error().message(e.getErrMsg());
        }
    }

}

