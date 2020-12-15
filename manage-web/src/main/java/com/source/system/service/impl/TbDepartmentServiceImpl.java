package com.source.system.service.impl;

import com.source.config.BusinessException;
import com.source.response.ResultCode;
import com.source.system.bean.TbDepartment;
import com.source.system.mapper.TbDepartmentMapper;
import com.source.system.service.TbDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
@Service
public class TbDepartmentServiceImpl extends ServiceImpl<TbDepartmentMapper, TbDepartment> implements TbDepartmentService {

    @Autowired
    private TbDepartmentMapper tbDepartmentMapper;

    @Override
    public List<TbDepartment> findDeptAndCount() throws BusinessException {
        List<TbDepartment> deptAndCount = tbDepartmentMapper.findDeptAndCount();
        if (StringUtils.isNotEmpty(deptAndCount.toString())){
            return deptAndCount;
        }else {
            throw new BusinessException(ResultCode.DEPARTMENT_IS_NOT.getCode(),ResultCode.DEPARTMENT_IS_NOT.getMessage());
        }
    }
}
