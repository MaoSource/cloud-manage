package com.source.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.source.config.BusinessException;
import com.source.system.bean.TbUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
public interface TbUserService extends IService<TbUser> {

    IPage<TbUser> findUserPage(Page<TbUser> page, String username, String nickname, String email, Integer sex, Long departmentId, String startDate, String endDate);

    void addUser(TbUser user) throws BusinessException;

}
