package com.msb.dao.impl;

import com.msb.dao.EmpDao;
import com.msb.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Repository
public class EmpDaoImpl implements EmpDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findEmpCount() {
        /*
        * 查询员工个数
        * jdbcTemplate.queryForObject
        * 1 SQL语句
        * 2 返回值类型字节码
        *
        * */
        String sql="select count(1) from emp";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Emp findByEmpno(int empno) {
        /*
        * 查询单个员工对象
        * 1 SQL语句
        * 2 RowMapper接口的一个实现类对象,用于指定结果集用什么实体类进行封装
        * 3 SQL语句中需要传入的参数 可变参数
        *
        * */
        String sql ="select * from emp where empno =?";
        BeanPropertyRowMapper<Emp> rowMapper=new BeanPropertyRowMapper<Emp>(Emp.class);
        Emp emp = jdbcTemplate.queryForObject(sql, rowMapper, empno);
        return emp;
    }

    @Override
    public List<Emp> findByDeptno(int deptno) {
        /*
        * 查询多个员工对象集合
        * jdbcTemplate.query()
         * 1 SQL语句
         * 2 RowMapper接口的一个实现类对象,用于指定结果集用什么实体类进行封装
         * 3 SQL语句中需要传入的参数 可变参数
        * */
        String sql ="select * from emp where deptno =?";
        BeanPropertyRowMapper<Emp> rowMapper=new BeanPropertyRowMapper<Emp>(Emp.class);

        List<Emp> emps = jdbcTemplate.query(sql, rowMapper, deptno);
        return emps;
    }

    @Override
    public int addEmp(Emp emp) {

        /*
        * 增加员工信息
        * jdbcTemplate.update
        * 1 sql
        * 2 SQL语句中的需要的参数 可变参数
        *
        * */
        String sql="insert into emp values(DEFAULT,?,?,?,?,?,?,?)";
        Object[] args ={emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno()};
        return jdbcTemplate.update(sql,args);
    }

    @Override
    public int updateEmp(Emp emp) {
        String sql="update emp set ename =?,job=?,mgr=?,hiredate=?,sal=?,comm=?,deptno =? where  empno=?";
        Object[] args ={emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno(),emp.getEmpno()};
        return jdbcTemplate.update(sql,args);
    }

    @Override
    public int deleteEmp(int empno) {
        String sql="delete from emp where empno=?";
        return jdbcTemplate.update(sql,empno);
    }
}
