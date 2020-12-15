package com.source.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.source.config.BusinessException;
import com.source.response.ResultCode;
import com.source.system.bean.TbDepartment;
import com.source.system.bean.TbUser;
import com.source.system.enums.UserStatusEnum;
import com.source.system.enums.UserTypeEnum;
import com.source.system.mapper.TbDepartmentMapper;
import com.source.system.mapper.TbUserMapper;
import com.source.system.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author source
 * @since 2020-12-08
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbDepartmentMapper tbDepartmentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<TbUser> findUserPage(Page<TbUser> page, String username, String nickname, String email, Integer sex, Long departmentId, String startDate, String endDate) {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(departmentId)) {
            queryWrapper.eq("department_id", departmentId);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq("username", username);
        }
        if (!StringUtils.isEmpty(email)) {
            queryWrapper.eq("email", email);
        }
        if (!StringUtils.isEmpty(sex)) {
            queryWrapper.eq("sex", sex);
        }
        if (!StringUtils.isEmpty(nickname)) {
            queryWrapper.eq("nickname", nickname);
        }
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            queryWrapper.between("create_time", startDate, endDate);
        }
        IPage<TbUser> userPage = tbUserMapper.findUserPage(page, queryWrapper);
        return userPage;
    }

    @Override
    public void addUser(TbUser user) throws BusinessException {

        // 获取用户名防止重复
        String username = user.getUsername();

        // 获取部门ID防止重复
        Long departmentId = user.getDepartmentId();

        QueryWrapper<TbUser> objectQueryWrapper = new QueryWrapper<>();

        objectQueryWrapper.eq("username", username);

        Integer integer = baseMapper.selectCount(objectQueryWrapper);

        if (integer != 0) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(),
                    ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }

        TbDepartment tbDepartment = tbDepartmentMapper.selectById(departmentId);

        if (tbDepartment == null) {
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),
                    ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }

//        设置salt取32位
        String salt = UUID.randomUUID().toString().substring(0, 32);
        user.setSalt(salt);

        // 设置创建时间和更新时间
//        user.setCreateTime(new Date());
//        user.setModifiedTime(new Date());

//        使用security进行密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 用户类型
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());

        // 设置状态
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());

        // 设置是否删除
        user.setDeleted(false);

        baseMapper.insert(user);

    }
}
