package cn.edu.dao;

import cn.edu.model.ShopOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(ShopOrder record);

    int insertSelective(ShopOrder record);

    ShopOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(ShopOrder record);

    int updateByPrimaryKey(ShopOrder record);
}