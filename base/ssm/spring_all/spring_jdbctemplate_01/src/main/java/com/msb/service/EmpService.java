package com.msb.service;

import com.msb.pojo.Emp;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface EmpService {

    int findEmpCount();


    Emp findByEmpno(int empno);


    List<Emp> findByDeptno(int deptno);


    int addEmp(Emp emp);


    int updateEmp(Emp emp);

    int deleteEmp(int empno);




}
