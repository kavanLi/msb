package com.bobo.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.internalcommon.domain.pojo.User;

/**
 * MyBatisPlus中 Mapper接口必须继承 BaseMapper<User1>
 *     同时指定对应的实体类
 */
public interface UserMapper extends BaseMapper<User> {
}
