package com.mashibing.mapper;

import com.mashibing.domain.User;

import java.util.List;

/**
 * @author spikeCong
 * @date 2022/10/29
 **/
public interface UserMapper {

    public List<User> findAll();

    User findById(Integer id);

    int deleteById(int id);
}
