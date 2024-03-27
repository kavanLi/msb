package com.bobo.mp.mapper;

import java.util.List;
import java.util.Map;

import com.mashibing.internalcommon.domain.pojo.DynaAmsOrgprovisions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kavanLi-R7000
* @description 针对表【DYNA_AMS_ORGPROVISIONS】的数据库操作Mapper
* @createDate 2023-12-11 21:11:36
* @Entity com.bobo.mp.pojo.DynaAmsOrgprovisions
*/
public interface DynaAmsOrgprovisionsMapper extends BaseMapper<DynaAmsOrgprovisions> {
    List <DynaAmsOrgprovisions> list4Summary(@Param("params") Map<String, Object> params);

    List<Map <String, Object>> list4Summary1(@Param("params") Map<String, Object> params);

    List <DynaAmsOrgprovisions> list4Summary2(@Param("params") Map<String, Object> params);
}




