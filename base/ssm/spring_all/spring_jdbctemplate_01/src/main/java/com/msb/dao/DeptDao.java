package com.msb.dao;

import com.msb.pojo.Dept;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface DeptDao {
    int[] deptBatchAdd(List<Dept> depts);

    int[] deptBatchUpdate(List<Dept> depts);

    int[] deptBatchDelete(List<Integer> deptnos);
}
