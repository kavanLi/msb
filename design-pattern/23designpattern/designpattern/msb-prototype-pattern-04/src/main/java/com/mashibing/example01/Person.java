package com.mashibing.example01;

import java.io.Serializable;

/**
 * @author spikeCong
 * @date 2022/9/21
 **/
public class Person implements Serializable {

    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
