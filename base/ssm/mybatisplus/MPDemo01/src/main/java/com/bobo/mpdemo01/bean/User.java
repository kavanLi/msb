package com.bobo.mpdemo01.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @TableName 标识实体类对应的表名
 */
@AllArgsConstructor
@ToString
@Data
public class User {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long uid; // 表明uid就是主键字段对应的属性
    @TableField("name") // 表结构中的name属性和name属性对应
    private String name;
    private Integer age;
    private String email;
    // value:0 表示没有删除 delval:1 表示删除的值
   // @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}

