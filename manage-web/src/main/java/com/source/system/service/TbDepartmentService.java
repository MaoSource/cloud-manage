package com.source.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.source.config.BusinessException;
import com.source.system.bean.TbDepartment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
public interface TbDepartmentService extends IService<TbDepartment> {

    List<TbDepartment> findDeptAndCount() throws BusinessException;

}
