package com.mashibing.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.mashibing.tank.GameObject;

public class RectDecorator extends GODecorator {

	public RectDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, super.go.getWidth(), super.go.getHeight());
		g.setColor(c);
	}

	@Override
	public int getWidth() {
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		return super.go.getHeight();
	}
}
