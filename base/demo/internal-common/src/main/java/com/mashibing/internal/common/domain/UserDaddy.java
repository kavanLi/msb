package com.mashibing.internal.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 
 * @TableName t_user
 */
@Data
public class UserDaddy implements Serializable {
    /**
     * 主键ID
     */
    private String appid;

    /**
     * 姓名
     */
    private String createUser;

    /**
     * 年龄
     */

    /**
     * 邮箱
     */
    private String updateUser;

    /**
     * 是否删除 0 正常 1删除
     */


}