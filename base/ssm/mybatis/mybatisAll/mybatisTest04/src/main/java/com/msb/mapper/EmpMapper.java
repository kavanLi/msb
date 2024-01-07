package com.msb.mapper;

import com.msb.pojo.Emp;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface EmpMapper {

    List<Emp> findEmpsByDeptno(int deptno);


    Emp findByEmpno(int empno);


}
