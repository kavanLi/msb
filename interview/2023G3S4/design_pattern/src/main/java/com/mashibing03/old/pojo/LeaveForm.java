package com.mashibing03.old.pojo;

/**
 * 请假单
 * @author spikeCong
 * @date 2023/3/21
 **/
public class LeaveForm {

    private Employee employee; //员工

    private String reason;  //请假原因

    private int days;   //天数

    private int type;  //类型: 0-病假 , 1-婚丧假 , 2-年假

    public LeaveForm(com.mashibing03.old.pojo.Employee employee, String reason, int days, int type) {
        this.employee = employee;
        this.reason = reason;
        this.days = days;
        this.type = type;
    }

    public com.mashibing03.old.pojo.Employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.mashibing03.old.pojo.Employee employee) {
        this.employee = employee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LeaveForm{" +
                "employee=" + employee +
                ", reason='" + reason + '\'' +
                ", days=" + days +
                ", type=" + type +
                '}';
    }
}
