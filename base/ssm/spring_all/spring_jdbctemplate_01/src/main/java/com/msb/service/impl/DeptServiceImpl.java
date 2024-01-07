package com.msb.service.impl;

import com.msb.dao.DeptDao;
import com.msb.pojo.Dept;
import com.msb.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public int[] deptBatchAdd(List<Dept> depts) {
        return deptDao.deptBatchAdd(depts);
    }

    @Override
    public int[] deptBatchUpdate(List<Dept> depts) {
        return deptDao.deptBatchUpdate(depts);
    }

    @Override
    public int[] deptBatchDelete(List<Integer> deptnos) {
        return deptDao.deptBatchDelete(deptnos);
    }
}
