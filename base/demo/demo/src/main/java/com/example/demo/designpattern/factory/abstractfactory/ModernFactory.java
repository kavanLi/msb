package com.example.demo.designpattern.factory.abstractfactory;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 17:00
 * To change this template use File | Settings | File and Code Templates.
 */
public class ModernFactory extends AbstractFactory {
    @Override
    Food createFood() {
        return new Bread();
    }

    @Override
    Vehicle createVehicle() {
        return new Car();
    }

    @Override
    Weapon createWeapon() {
        return new AK47();
    }
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
