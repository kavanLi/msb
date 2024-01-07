package com.bobo.mybatisx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobo.mybatisx.domain.TUser;
import com.bobo.mybatisx.service.TUserService;
import com.bobo.mybatisx.mapper.TUserMapper;
import org.springframework.stereotype.Service;

/**
* @author dpb
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-04-22 11:26:06
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

}




