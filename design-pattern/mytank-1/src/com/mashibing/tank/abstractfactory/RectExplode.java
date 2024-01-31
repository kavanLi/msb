package com.mashibing.tank.abstractfactory;

import java.awt.*;
import java.util.Stack;

import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.TankFrame;

//public class RectExplode extends BaseExplode{
//    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
//    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
//
//    private int x,y;
//    private TankFrame tf = null;
//    private boolean living = true;
//
//    private int step = 0;
//
//
//    public RectExplode(int x, int y, TankFrame tf) {
//        this.x = x;
//        this.y = y;
//        this.tf = tf;
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        //g.drawImage(ResourceMgr.explodes[step++], x, y, null);
//
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillRect(x, y, 10* step, 10* step);
//        step++;
//
//        if (step >= 10) {
//            tf.explodes.remove(this);
//        }
//
//        g.setColor(c);
//
//        //if (step >= ResourceMgr.explodes.length) tf.explodes.remove(this);
//    }
//}
