package com.petris.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PieceI extends Piece {

	public PieceI() {
		this.setState(0);
	}

	public PieceI(Color c) {
		super(c);
		blocks[0] = new Rectangle(800/2,0,Piece.BLOCK_HEIGHT,Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800/2,Piece.BLOCK_HEIGHT,Piece.BLOCK_HEIGHT,Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800/2,Piece.BLOCK_HEIGHT*2,Piece.BLOCK_HEIGHT,Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800/2,Piece.BLOCK_HEIGHT*3,Piece.BLOCK_HEIGHT,Piece.BLOCK_HEIGHT);
	}

	@Override
	public void rotate() {
		if(getState() == 0) {
			blocks[1].setX(blocks[0].getX()-Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[0].getY());
			blocks[2].setX(blocks[1].getX()-Piece.BLOCK_HEIGHT);
			blocks[2].setY(blocks[0].getY());
			blocks[3].setX(blocks[2].getX()-Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[0].getY());
			setState(1);
		}
		else {
			blocks[1].setX(blocks[0].getX());
			blocks[1].setY(blocks[0].getY()+Piece.BLOCK_HEIGHT);
			blocks[2].setX(blocks[0].getX());
			blocks[2].setY(blocks[1].getY()+Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[0].getX());
			blocks[3].setY(blocks[2].getY()+Piece.BLOCK_HEIGHT);
			setState(0);
		}
	}

	@Override
	public boolean canRotate(Vector2[] v) {
		// TODO Auto-generated method stub
		return false;
	}
}
