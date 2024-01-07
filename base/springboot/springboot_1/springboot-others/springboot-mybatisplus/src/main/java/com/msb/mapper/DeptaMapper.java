package com.msb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.pojo.Dept;
import com.msb.pojo.Depta;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

public interface DeptaMapper extends BaseMapper<Depta> {
    public Depta findByDeptno(int deptno);
}
