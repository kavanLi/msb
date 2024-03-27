package com.bobo.mp.mapper;

import com.mashibing.internalcommon.domain.pojo.DynaAmsUserOptLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author kavanLi-R7000
* @description 针对表【DYNA_AMS_USER_OPT_LOG】的数据库操作Mapper
* @createDate 2023-12-15 15:51:20
* @Entity com.mashibing.internalcommon.domain.pojo.DynaAmsUserOptLog
*/

public interface DynaAmsUserOptLogMapper extends BaseMapper<DynaAmsUserOptLog> {
    Boolean insertDynaAmsUserOptLog(DynaAmsUserOptLog dynaAmsUserOptLog);
}




