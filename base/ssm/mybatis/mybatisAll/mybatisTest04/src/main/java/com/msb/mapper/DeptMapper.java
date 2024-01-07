package com.msb.mapper;

import com.msb.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface DeptMapper {
    Dept findDeptByDeptno(int deptno);


    @Select("select * from dept where deptno =#{deptno}")
    Dept findByDeptno(int deptno);

    @Update("update dept set dname =#{dname}, loc =#{loc} where deptno =#{deptno}")
    int updateDept(Dept dept);

    @Insert("insert into dept values(DEFAULT,#{dname},#{loc})")
    int addDept(Dept dept);

    @Delete("delete from dept where deptno =#{deptno}")
    int removeDept(int deptno);

}
