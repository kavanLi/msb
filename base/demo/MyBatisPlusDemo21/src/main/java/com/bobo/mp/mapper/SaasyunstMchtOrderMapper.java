package com.bobo.mp.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mashibing.internalcommon.domain.pojo.SaasyunstMchtOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lijj
 */
@Mapper
public interface SaasyunstMchtOrderMapper extends BaseMapper <SaasyunstMchtOrder> {

    /**
     * 带分页的Left Join级联查询
     * IPage <SaasyunstMchtOrderDTO> iPage4Join = new Page <>(req.getPageNo(), req.getPageSize());
     * iPage4Join = saasyunstMchtOrderMapper.getSaasyunstMchtOrderJoinList(iPage4Join, params);
     * @param page
     * @param params
     * @return
     * @param <P>
     */
    <P extends IPage> P getSaasyunstMchtOrderJoinList(P page, @Param("params") Map <String, Object> params);

    List <SaasyunstMchtOrder> list4Summary(@Param("params") Map <String, Object> params);
}
