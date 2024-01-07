package com.bobo.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bobo.mp.domain.pojo.Dept;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

public interface DeptMapper extends BaseMapper <Dept> {
    public Dept findByDeptno(int deptno);
}
