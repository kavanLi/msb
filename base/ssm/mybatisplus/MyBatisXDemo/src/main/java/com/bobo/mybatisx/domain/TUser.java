package com.bobo.mybatisx.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class TUser implements Serializable {
    /**
     * 主键ID
     */
    private Long uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否删除 0 正常 1删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}