package com.source.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.source.system.bean.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    IPage<TbUser> findUserPage(Page<TbUser> page,@Param(Constants.WRAPPER) QueryWrapper<TbUser> wrapper);

}
