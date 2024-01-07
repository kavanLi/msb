package com.msb.mapper;

import com.msb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Mapper
public interface UserMapper {
    List<User> selectAll();
}
