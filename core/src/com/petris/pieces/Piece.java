package com.petris.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/*
 * Abstract class that rappresent a piece.
 * Every component of the piece is a Rectangle.
 */
public abstract class Piece {
	
    public static final int BLOCK_HEIGHT = 20;
    private int state;
    protected Rectangle[] blocks;
    private Texture texture;

    public Piece(Piece p) {
        this.state = p.state;
        for (int i = 0; i < 4; i++) {
            this.blocks[i] = new Rectangle(p.blocks[i]);
        }
        this.texture = p.texture;
    }

    public Piece(String path) {
        blocks = new Rectangle[4];
        texture = new Texture(Gdx.files.internal(path));
    }

    public Rectangle[] getBlocks() {
        return blocks;
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract void rotate();

    public abstract void goInHold();

    public abstract void goToStart();
    
    public void move() {
        for (Rectangle i : blocks)
            i.y += BLOCK_HEIGHT;
    }

    public boolean moveRight() {
        for (Rectangle i : blocks)
            i.x += BLOCK_HEIGHT;
        return true;
    }

    public boolean moveLeft() {
        for (Rectangle i : blocks)
            i.x -= BLOCK_HEIGHT;
        return true;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        if (this.state < 0)
            this.state = 0;
    }
}
