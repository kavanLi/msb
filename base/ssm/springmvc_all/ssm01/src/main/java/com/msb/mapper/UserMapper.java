package com.msb.mapper;

import com.msb.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface UserMapper {

    User findUser( String uname,  String password);

    List<User> findAllUser();
}
