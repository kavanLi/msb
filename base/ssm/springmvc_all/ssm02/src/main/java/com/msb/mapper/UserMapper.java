package com.msb.mapper;

import com.msb.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface UserMapper {
    @Select("select * from user where uname =#{param1} and password =#{param2}")
    User findUser(String uname, String password);
}
