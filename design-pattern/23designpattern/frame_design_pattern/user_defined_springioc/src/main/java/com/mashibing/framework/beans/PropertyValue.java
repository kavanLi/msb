package com.mashibing.framework.beans;

/**
 * 该类用来封装Bean标签下的property子标签属性
 *      1.name属性
 *      2.ref属性
 *      3.value属性: 给基本数据类型或者String类型数据赋值的值
 * @author spikeCong
 * @date 2022/10/27
 **/
public class PropertyValue {

    private String name;

    private String ref;

    private String value;

    public PropertyValue() {
    }

    public PropertyValue(String name, String ref, String value) {
        this.name = name;
        this.ref = ref;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
