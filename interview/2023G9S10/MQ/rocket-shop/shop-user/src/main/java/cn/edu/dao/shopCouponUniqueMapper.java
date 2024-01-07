package cn.edu.dao;

import cn.edu.model.shopCouponUnique;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface shopCouponUniqueMapper {
    int insert(shopCouponUnique record);

    int insertSelective(shopCouponUnique record);
}