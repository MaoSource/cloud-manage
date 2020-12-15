package com.source.system.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/09/10:51
 * @Description:
 */
@Data
public class UserVo {
    private String username;

    private String nickname;

    private String email;

    private Integer sex;

    private Long departmentId;
}
