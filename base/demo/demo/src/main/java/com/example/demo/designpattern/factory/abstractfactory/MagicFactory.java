package com.example.demo.designpattern.factory.abstractfactory;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 16:36
 * To change this template use File | Settings | File and Code Templates.
 */
public class MagicFactory extends AbstractFactory{
    @Override
    Food createFood() {
        return new MushRoom();
    }

    @Override
    Vehicle createVehicle() {
        return new Broom();
    }

    @Override
    Weapon createWeapon() {
        return new MagicStick();
    }
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
