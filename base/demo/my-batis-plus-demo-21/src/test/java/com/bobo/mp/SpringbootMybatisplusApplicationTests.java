package com.bobo.mp;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashibing.internal.common.domain.pojo.Dept;
import com.bobo.mp.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisplusApplicationTests {


    @Autowired
    DeptService deptService;


    @Test
    public void testFindAll(){
        List<Dept> list = deptService.list();
        for (Dept dept : list) {
            System.out.println(dept);
        }
    }
    // 查询集合
    @Test
    public void testQueryWrapper(){
        // 部门号》=20
        // QueryWrapper 作用就是在原本的SQL语句后面拼接where条件
        // selec * from where      delete from dept where  update dept set ...  where ....
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        //queryWrapper.ge("deptno", 20).eq("dname", "ACCOUNTING").likeRight("dname", "A");
        //queryWrapper.likeRight("dname", "A");
        List<Dept> list = deptService.list(queryWrapper);
        for (Dept dept : list) {
            System.out.println(dept);
        }


    }
    // 查询单个
    @Test
    public void testQueryWrapper2(){
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deptno", 20);
        Dept dept = deptService.getOne(queryWrapper);
        System.out.println(dept);

    }

    // 增加
    @Test
    public void testAdd(){
        boolean save = deptService.save(new Dept(null, "aaa", "bbb"));
        System.out.println(save);
    }

    // 修改
    @Test
    public void testUpdate(){
        // 要更新的数据
        Dept dept =new Dept();
        dept.setDname("xxx");
        dept.setLoc("yyy");
        // 更新的条件
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deptno", 41);
        boolean update = deptService.update(dept, queryWrapper);
        System.out.println(update);
    }
    // 删除
    @Test
    public void testRemove(){
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deptno", 41);
        boolean remove = deptService.remove(queryWrapper);
        System.out.println(remove);
    }

    // 测试分页
    @Test
    public void testPage(){
        // 当前页  页大小
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        //queryWrapper.likeRight("dname", "A");
        Page<Dept> page = deptService.page(new Page<>(1, 2), queryWrapper);
        // 当前页数据  总页数  总记录数  当前页  页大小 ... ..
        List<Dept> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("总页数:"+page.getPages());
        System.out.println("总记录数:"+page.getTotal());
        System.out.println("当前页:"+page.getCurrent());
        System.out.println("页大小:"+page.getSize());
    }



}
