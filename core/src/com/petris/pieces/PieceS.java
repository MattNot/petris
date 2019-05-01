package com.petris.pieces;

import com.badlogic.gdx.math.Rectangle;
import com.map.Map;

public class PieceS extends Piece {

	public PieceS(String path) {
		super(path);
		this.goToStart();
	}

	@Override
	public void rotate() {
	}

	@Override
	public void goInHold() {
		blocks[0].setX(Map.START_HOLD_X);
		blocks[0].setY(Map.START_HOLD_Y);
		blocks[1].setX(Map.START_HOLD_X+Piece.BLOCK_HEIGHT);
		blocks[1].setY(Map.START_HOLD_Y);
		blocks[2].setX(Map.START_HOLD_X);
		blocks[2].setY(Map.START_HOLD_Y+Piece.BLOCK_HEIGHT);
		blocks[3].setX(Map.START_HOLD_X+Piece.BLOCK_HEIGHT);
		blocks[3].setY(Map.START_HOLD_Y+Piece.BLOCK_HEIGHT);
		this.setState(0);
	}

	@Override
	public void goToStart() {
		blocks[0] = new Rectangle(800 / 2, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT,
				Piece.BLOCK_HEIGHT);
	}

}
