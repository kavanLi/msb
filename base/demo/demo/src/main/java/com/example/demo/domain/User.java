package com.example.demo.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName t_user
 */
@Data
@ToString(callSuper=true)
public class User extends UserDaddy implements Serializable {
    /**
     * 主键ID
     */

    private Dept dept;
    private Map<String, String> map;
    private List <String> list;
    private Long uid;
    private JSONObject BODY;
    private JSONObject INFO;

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