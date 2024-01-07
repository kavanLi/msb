package cn.edu.dao;

import cn.edu.model.shopGoodsUnique;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface shopGoodsUniqueMapper {
    int insert(shopGoodsUnique record);

    int insertSelective(shopGoodsUnique record);
}