package com.source.system.mapper;

import com.source.system.bean.TbDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
public interface TbDepartmentMapper extends BaseMapper<TbDepartment> {

    List<TbDepartment> findDeptAndCount();

}
