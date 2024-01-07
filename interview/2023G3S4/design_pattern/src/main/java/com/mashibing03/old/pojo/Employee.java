package com.mashibing03.old.pojo;

/**
 * 员工
 * @author spikeCong
 * @date 2023/3/21
 **/
public class Employee {

    private String name;  //姓名

    private int levle;   //级别: 1~5 普通员工

    public Employee() {
    }

    public Employee(String name, int levle) {
        this.name = name;
        this.levle = levle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevle() {
        return levle;
    }

    public void setLevle(int levle) {
        this.levle = levle;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", levle=" + levle +
                '}';
    }
}
