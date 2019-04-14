package com.petris.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Piece {
	public static final int BLOCK_HEIGHT = 20;
	private int state;
	protected Rectangle [] blocks;
	private Color c;

	public Piece() {
		blocks = new Rectangle[4];
	}
	public Piece(Color c) {
		blocks = new Rectangle[4];
		this.c = c;
	}
	public Rectangle[] getBlocks() {
		return blocks;
	}
	public Color getC() {
		return c;
	}
	public void setC(Color c) {
		this.c = c;
	}
	public abstract void rotate();
	/*
	 * TODO
	 * @argument {Vector2[]} v - Non so se è il miglior modo per ricevere le coordinate di tutti i blocchi vicini
	 */
	protected abstract boolean canRotate(Vector2[] v);
	
	public void move() {
		for(Rectangle i : blocks)
			i.y += BLOCK_HEIGHT;
	}
	
	public boolean moveRight() {
		for(Rectangle i : blocks)
			i.x += BLOCK_HEIGHT;
		return true;
	}
	public boolean moveLeft() {
		for(Rectangle i : blocks)
			i.x -= BLOCK_HEIGHT;
		return true;
	}
	protected int getState() {
		return state;
	}
	protected void setState(int state) {
		this.state = state;
	}
}
