package com.example.demo.designpattern.factory.factorymethod;

public class CarFactory {
    public Moveable create() {
        System.out.println("a car created!");
        return new Car();
    }
}
