package com.petris.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PieceS extends Piece {

	public PieceS() {
	}

	public PieceS(Color c) {
		super(c);
		blocks[0] = new Rectangle(800/2, 0, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800/2, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800/2+Piece.BLOCK_HEIGHT, 0, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800/2+Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
	}

	@Override
	public void rotate() {}
	@Override
	protected boolean canRotate(Vector2[] v) {return true;}


}
