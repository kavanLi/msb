package com.mashibing.tank.abstractfactory;

import java.awt.*;
import java.util.Random;

import com.mashibing.tank.Bullet;
//import com.mashibing.tank.DefaultFireStrategy;
import com.mashibing.tank.Dir;
import com.mashibing.tank.FireStrategy;
import com.mashibing.tank.FourDirFireStrategy;
import com.mashibing.tank.Group;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

//public class RectTank extends BaseTank {
    //int x, y;
    //Dir dir = Dir.DOWN;
    //private static final int SPEED = 5;
    //
    //private Random random = new Random();
    //
    //public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    //public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    //
    ////public Rectangle rect = new Rectangle();
    //
    //private boolean moving = true;
    //
    //TankFrame tf = null;
    //
    //private boolean living = true;
    //
    ////Group group = Group.BAD;
    //
    //FireStrategy fs = new FourDirFireStrategy();
    //
    //public RectTank(int x, int y, Dir dir, Group group, TankFrame tf) {
    //    this.x = x;
    //    this.y = y;
    //    this.dir = dir;
    //    this.group = group;
    //    this.tf = tf;
    //    rect.x = this.x;
    //    rect.y = this.y;
    //    rect.width = WIDTH;
    //    rect.height = HEIGHT;
    //
    //    if (group == Group.GOOD) {
    //        fs = new FourDirFireStrategy();
    //    } else {
    //        fs = new DefaultFireStrategy();
    //    }
    //}
    //
    //public void paint(Graphics g) {
    //    if (!living) tf.tanks.remove(this);
    //
    //    Color c = g.getColor();
    //    g.setColor(this.group == Group.GOOD ? Color.RED : Color.BLUE);
    //    g.fillRect(x, y, 40, 40);
    //    g.setColor(c);
    //
    //    move();
    //}
    //
    //public Group getGroup() {
    //    return group;
    //}
    //
    //public void setGroup(Group group) {
    //    this.group = group;
    //}
    //
    //public void setDir(Dir dir) {
    //    this.dir = dir;
    //}
    //
    //public Dir getDir() {
    //    return dir;
    //}
    //
    //public boolean isMoving() {
    //    return moving;
    //}
    //
    //public void setMoving(boolean moving) {
    //    this.moving = moving;
    //}
    //
    //public int getX() {
    //    return x;
    //}
    //
    //public void setX(int x) {
    //    this.x = x;
    //}
    //
    //public int getY() {
    //    return y;
    //}
    //
    //public void setY(int y) {
    //    this.y = y;
    //}
    //
    //private void move() {
    //    if (!moving) return;
    //
    //    switch (dir) {
    //        case LEFT:
    //            x -= SPEED;
    //            break;
    //        case UP:
    //            y -= SPEED;
    //            break;
    //        case RIGHT:
    //            x += SPEED;
    //            break;
    //        case DOWN:
    //            y += SPEED;
    //            break;
    //    }
    //
    //    rect.x = this.x;
    //    rect.y = this.y;
    //
    //    if (this.group == Group.BAD) {
    //        if (random.nextInt(100) > 95) {
    //            this.fire();
    //            randomDir();
    //        }
    //    }
    //
    //    boundsCheck();
    //
    //}
    //
    //private void boundsCheck() {
    //    if (this.x < 0) {
    //        x = 0;
    //    } else if (this.y < 30) {
    //        y = 30;
    //    } else if (this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH) {
    //        x = TankFrame.GAME_WIDTH - RectTank.WIDTH;
    //    } else if (this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT) {
    //        y = TankFrame.GAME_HEIGHT - RectTank.HEIGHT;
    //    }
    //
    //}
    //
    //private void randomDir() {
    //    this.dir = Dir.values()[random.nextInt(4)];
    //}
    //
    //public void fire() {
    //    //fs.fire(this);
    //    int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
    //    int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
    //
    //    Dir[] dirs = Dir.values();
    //    for (Dir dir : dirs) {
    //        this.tf.gf.createBullet(bX, bY, dir, this.group, this.tf);
    //        //new Bullet(bX, bY, dir, t.group, t.tf);
    //    }
    //}
    //
    //public void die() {
    //    this.living = false;
    //}
//}
