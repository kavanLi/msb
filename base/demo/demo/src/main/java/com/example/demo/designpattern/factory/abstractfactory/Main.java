package com.example.demo.designpattern.factory.abstractfactory;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 19:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class Main {
    /* fields -------------------------------------------------------------- */
    public static void main(String[] args) {
        ModernFactory modernFactory = new ModernFactory();
        modernFactory.createFood().printName();
        modernFactory.createVehicle().go();
        modernFactory.createWeapon().shoot();    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
