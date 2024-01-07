package com.example.demo.thread;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-11 20:10
 * To change this template use File | Settings | File and Code Templates.
 */
public class Task1 implements Delayed {

    private String name;

    private Long time;

    public Task1(String name, Long delay) {
        this.name = name;
        this.time = System.currentTimeMillis() + delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - System.currentTimeMillis(), unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (time - ((Task1)(o)).getTime());
    }
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
