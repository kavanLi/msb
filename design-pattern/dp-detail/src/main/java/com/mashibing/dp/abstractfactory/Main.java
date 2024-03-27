package com.mashibing.dp.abstractfactory;

public class Main {
    public static void main(String[] args) {
        //AbastractFactory f = new ModernFactory();
        AbastractFactory f = new MagicFactory();

        Vehicle c = f.createVehicle();
        c.go();
        Weapon w = f.createWeapon();
        w.shoot();
        Food b = f.createFood();
        b.printName();
    }
}
