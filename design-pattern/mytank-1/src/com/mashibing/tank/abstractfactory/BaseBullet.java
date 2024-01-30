package com.mashibing.tank.abstractfactory;

import java.awt.*;

import com.mashibing.tank.Tank;

public abstract class BaseBullet {

    public  abstract  void paint(Graphics g);

    public abstract void collideWith(BaseTank tank);
}
