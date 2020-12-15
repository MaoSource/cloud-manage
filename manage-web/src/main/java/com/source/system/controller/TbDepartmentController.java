package com.source.system.controller;


import com.source.config.BusinessException;
import com.source.response.Result;
import com.source.system.bean.TbDepartment;
import com.source.system.service.TbDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/system/tb-department")
@Api(value = "部门管理", tags = "部门管理接口")
public class TbDepartmentController {

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @GetMapping("/findDeptAndCount")
    @ApiOperation("查询部门及人数")
    public Result findDeptAndCount() throws BusinessException {
        List<TbDepartment> deptAndCount = tbDepartmentService.findDeptAndCount();
        return Result.ok().data("dept",deptAndCount);

    }

}

