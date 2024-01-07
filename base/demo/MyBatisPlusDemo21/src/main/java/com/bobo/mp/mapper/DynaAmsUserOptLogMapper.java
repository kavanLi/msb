package com.bobo.mp.mapper;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.bobo.mp.domain.pojo.Dept;
import com.bobo.mp.domain.pojo.DynaAmsUserOptLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kavanLi-R7000
* @description 针对表【DYNA_AMS_USER_OPT_LOG】的数据库操作Mapper
* @createDate 2023-12-15 15:51:20
* @Entity com.bobo.mp.domain.pojo.DynaAmsUserOptLog
*/

public interface DynaAmsUserOptLogMapper extends BaseMapper<DynaAmsUserOptLog> {
    Boolean insertDynaAmsUserOptLog(DynaAmsUserOptLog dynaAmsUserOptLog);
}




