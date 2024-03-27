package com.mashibing.internalcommon.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;

//@TableName(value = "t_user")
@ToString
@Data
public class User {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long uid;
    // 如果数据库中是 user_name 实体类中是 userName这种方式MyBatisPlus会自动帮助我们完成 驼峰命名法的转换
    @TableField("name") // 表结构中字段和实体类中的属性不一致的情况下可以设置映射关系
    private String userName;
    private Integer age;
    private String email;
    // 0 表示没有删除 1 表示已经逻辑删除了
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}

