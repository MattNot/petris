package com.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle{
	private static final long serialVersionUID = 273836992715437376L;
	Color c;
	public Color getC() {
		return c;
	}
	public void setC(Color c) {
		this.c = c;
	}
	public Block(Color c, Rectangle r) {
		super(r);
		this.c = c;
	}

}
