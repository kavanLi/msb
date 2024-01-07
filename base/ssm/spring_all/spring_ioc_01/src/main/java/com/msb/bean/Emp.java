package com.msb.bean;


/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Emp {
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept +
                '}';
    }
}
