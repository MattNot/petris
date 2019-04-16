package com.petris.pieces;

import com.badlogic.gdx.math.Rectangle;
import com.map.Map;

public class PieceS extends Piece {

	public PieceS(String path) {
		super(path);
		blocks[0] = new Rectangle(800 / 2, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[1] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[2] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
		blocks[3] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT,
				Piece.BLOCK_HEIGHT);
	}

	@Override
	public void rotate() {
	}

}
