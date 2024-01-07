package com.msb.mapper;

import com.msb.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Mapper
public interface EmpMapper {

    List<Emp> findAll();

}
