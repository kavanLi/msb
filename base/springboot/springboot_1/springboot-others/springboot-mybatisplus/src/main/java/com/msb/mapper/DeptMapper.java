package com.msb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

public interface DeptMapper extends BaseMapper<Dept> {
    public Dept findByDeptno(int deptno);
}
