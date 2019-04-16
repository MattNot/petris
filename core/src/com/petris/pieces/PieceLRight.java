package com.petris.pieces;

import com.badlogic.gdx.math.Rectangle;
import com.map.Map;

public class PieceLRight extends Piece {

	public PieceLRight(String path) {
		super(path);
		blocks[0] = new Rectangle(800 / 2, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT * 2, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT * 2, Piece.BLOCK_HEIGHT,
				Piece.BLOCK_HEIGHT);

	}

	@Override
	public void rotate() {
		if (this.getState() == 0) {
			blocks[0].setX(blocks[0].getX() + 2 * Piece.BLOCK_HEIGHT);
			blocks[1].setX(blocks[1].getX() + Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() - Piece.BLOCK_HEIGHT);
			blocks[2].setY(blocks[2].getY() - 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() - Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() - Piece.BLOCK_HEIGHT);
			this.setState(1);
		} else if (this.getState() == 1) {
			blocks[0].setY(blocks[0].getY() + 2 * Piece.BLOCK_HEIGHT);
			blocks[1].setX(blocks[1].getX() + Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() + Piece.BLOCK_HEIGHT);
			blocks[2].setX(blocks[2].getX() + 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() + Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() - Piece.BLOCK_HEIGHT);
			this.setState(2);
		} else if (this.getState() == 2) {
			blocks[0].setX(blocks[0].getX() - 2 * Piece.BLOCK_HEIGHT);
			blocks[1].setX(blocks[1].getX() - Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() + Piece.BLOCK_HEIGHT);
			blocks[2].setY(blocks[2].getY() + 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() + Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() + Piece.BLOCK_HEIGHT);
			this.setState(3);
		} else if (this.getState() == 3) {
			blocks[0].setY(blocks[0].getY() - 2 * Piece.BLOCK_HEIGHT);
			blocks[1].setX(blocks[1].getX() - Piece.BLOCK_HEIGHT);
			blocks[1].setY(blocks[1].getY() - Piece.BLOCK_HEIGHT);
			blocks[2].setX(blocks[2].getX() - 2 * Piece.BLOCK_HEIGHT);
			blocks[3].setX(blocks[3].getX() - Piece.BLOCK_HEIGHT);
			blocks[3].setY(blocks[3].getY() + Piece.BLOCK_HEIGHT);
			this.setState(0);
		}
	}
}
