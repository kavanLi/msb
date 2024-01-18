package com.msb.stream;

import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private int age;
    private Gender gender;
    private Grade grade;

    public Person(String name, int age, Gender gender, Grade grade) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", grade=" + grade +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    enum Gender{
        MALE,FEMALE
    }
    enum Grade{
        ONE,TWO,THREE,FOUR;
    }

    public static void main(String[] args) {
        List<Person> peoples = Arrays.asList(
                new Person("小明",10,Gender.MALE,Grade.ONE),
                new Person("小林",12,Gender.FEMALE,Grade.ONE),
                new Person("李刚",13,Gender.MALE,Grade.TWO),
                new Person("小花",14,Gender.MALE,Grade.TWO),
                new Person("小兰",5,Gender.FEMALE,Grade.THREE),
                new Person("天涯",16,Gender.MALE,Grade.ONE),
                new Person("善缘",16,Gender.MALE,Grade.TWO),
                new Person("谷歌",43,Gender.FEMALE,Grade.FOUR),
                new Person("黎明",33,Gender.MALE,Grade.ONE)
        );

        // 1、得到所有学生的年龄列表
        // 或者我们可以转化为：Collectors.toSet() ，或者指定的集合类型：他默认是hashSet我们如果用TreeSet
        // 可以如下：Collectors.toCollection(TreeSet::new )
        List<Integer> ages = peoples.stream().map(s -> s.getAge()).collect(Collectors.toList());
        System.out.println("所有学生的年龄 ：" + ages);
       // 1、统计汇总信息
        IntSummaryStatistics ageSummaryStatistics = peoples.stream().collect(Collectors.summarizingInt(Person::getAge));
        System.out.println("年龄汇总：" + ageSummaryStatistics);
        //2、分块
        Map<Boolean, List<Person>> genders = peoples.stream().
                collect(Collectors.partitioningBy(s -> s.getGender() == Gender.MALE));
        //
        //System.out.println( "男女学生列表：" + genders);
        // 上述打印不直观
        MapUtils.verbosePrint(System.out,"男女学生列表",genders);
        // 3、分组
        Map<Grade, List<Person>> grades = peoples.stream()
                .collect(Collectors.groupingBy(Person::getGrade));
        MapUtils.verbosePrint(System.out,"学习班级列表",grades);
        // 4、得到所有班级的个数
        Map<Grade, Long> gradesCount = peoples.stream()
                .collect(Collectors.groupingBy(Person::getGrade,Collectors.counting()));
        MapUtils.verbosePrint(System.out,"班级学生个数列表：",gradesCount);

    }
}
