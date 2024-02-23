package com.bobo.mp.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bobo.mp.domain.pojo.SaasyunstMchtOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lijj
 */
@Mapper
public interface SaasyunstMchtOrderMapper extends BaseMapper <SaasyunstMchtOrder> {

    List <SaasyunstMchtOrder> list4Summary(@Param("params") Map <String, Object> params);
}
