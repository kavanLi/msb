package com.msb.service;

import com.msb.pojo.Emp;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface EmpService {
    List<Emp> findAll();

    List<Emp> findByPage(Integer pageNum, Integer pageSize);
}
