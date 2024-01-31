package com.mashibing.tank.abstractfactory;

import java.awt.*;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Explode;
import com.mashibing.tank.Group;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

//public class RectBullet extends BaseBullet {
    //private static final int SPEED = 10;
    //public static int WIDTH = ResourceMgr.bulletD.getWidth();
    //public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    //
    //Rectangle rect = new Rectangle();
    //
    //private int x,y;
    //private Dir dir;
    //private TankFrame tf = null;
    //private boolean living = true;
    //
    //private Group group = Group.BAD;
    //
    //public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
    //    this.x = x;
    //    this.y = y;
    //    this.dir = dir;
    //    this.group = group;
    //    this.tf = tf;
    //
    //    rect.x = this.x;
    //    rect.y = this.y;
    //    rect.width = WIDTH;
    //    rect.height = HEIGHT;
    //
    //    tf.bullets.add(this);
    //}
    //
    //public void paint(Graphics g) {
    //    if (!living) {
    //        tf.bullets.remove(this);
    //    }
    //    //switch (dir) {
    //    //    case LEFT:
    //    //        g.drawImage(ResourceMgr.bulletL, x, y, null);
    //    //        break;
    //    //    case UP:
    //    //        g.drawImage(ResourceMgr.bulletU, x, y, null);
    //    //        break;
    //    //    case RIGHT:
    //    //        g.drawImage(ResourceMgr.bulletR, x, y, null);
    //    //        break;
    //    //    case DOWN:
    //    //        g.drawImage(ResourceMgr.bulletD, x, y, null);
    //    //        break;
    //    //}
    //    Color c = g.getColor();
    //    g.setColor(Color.YELLOW);
    //    g.fillRect(x, y, 20, 20);
    //    g.setColor(c);
    //
    //
    //    move();
    //}
    //
    //private void move() {
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
    //    if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
    //        living = false;
    //    }
    //
    //}
    //
    //
    //@Override
    //public void collideWith(BaseTank tank) {
    //    if (this.group == tank.getGroup()) return;
    //
    //    if (rect.intersects(tank.rect)) {
    //        tank.die();
    //        this.die();
    //        int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
    //        int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
    //        tf.explodes.add(tf.gf.createExplode(eX, eY, tf));
    //    }
    //
    //}
    //
    //private void die() {
    //    this.living = false;
    //}
    //
    //public Group getGroup() {
    //    return group;
    //}
    //
    //public void setGroup(Group group) {
    //    this.group = group;
    //}
//}
