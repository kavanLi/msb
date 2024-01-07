package com.bobo.mybatisx.mapper;

import com.bobo.mybatisx.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author dpb
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-04-22 11:26:06
* @Entity com.bobo.mybatisx.domain.TUser
*/
public interface TUserMapper extends BaseMapper<TUser> {

    TUser queryUserById(@Param("id") Integer id);
}




