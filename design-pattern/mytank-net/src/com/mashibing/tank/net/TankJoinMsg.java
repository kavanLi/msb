package com.mashibing.tank.net;

public class TankJoinMsg {
	public int x, y;

	public TankJoinMsg(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "TankJoinMsg:" + x + "," + y;
	}
}
