package com.petris.pieces;

import com.badlogic.gdx.math.Rectangle;
import com.map.Map;

public class PieceZLeft extends Piece {

	public PieceZLeft(String path) {
		super(path);
		blocks[0] = new Rectangle(800 / 2 - Piece.BLOCK_HEIGHT, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800 / 2, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT,
				Piece.BLOCK_HEIGHT);

	}

	@Override
	public void rotate() {
		if (this.getState() == 0) {
			blocks[0].setX(blocks[0].getX() + Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() + Piece.BLOCK_HEIGHT);
			blocks[2].setX(blocks[2].getX() - Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() - 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() + Piece.BLOCK_HEIGHT);
			this.setState(1);
		} else if (this.getState() == 1) {
			blocks[0].setX(blocks[0].getX() - Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() - Piece.BLOCK_HEIGHT);
			blocks[2].setX(blocks[2].getX() + Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() + 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() - Piece.BLOCK_HEIGHT);
			this.setState(0);
		}
	}

	@Override
	public void goInHold() {

	}

	@Override
	public void goToStart() {

	}
}
