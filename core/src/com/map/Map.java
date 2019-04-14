package com.map;

import com.badlogic.gdx.math.Rectangle;
import com.petris.pieces.Piece;

public class Map {
	
	private Rectangle borders[];
	private Block map[][];
	
	public Map() {
		borders = new Rectangle[3];
		borders[0] = new Rectangle(200+90, 0, 10, 400);
		borders[1] = new Rectangle(400+100, 0, 10, 410);
		borders[2] = new Rectangle(200+90, 400, 210,10);
		map = new Block[20][10];
	}
	
	public boolean leftCollision(Piece P) {
		for(Rectangle i : P.getBlocks())
			if(i.getX() <= borders[0].getX() + Piece.BLOCK_HEIGHT)
				return false;
		/*for(Rectangle i : P.getBlocks())
			if(map[(int)(i.getY())/Piece.BLOCK_HEIGHT][((int)(i.getX()-300)/Piece.BLOCK_HEIGHT)-1] != null)
				return false;*/ //bisogna gestire le collisioni con altri blocchi. Teletrasporto?
		return true;
	}
	
	public boolean rightCollision(Piece P) {
		for(Rectangle i : P.getBlocks())
			if(i.getX() >= borders[1].getX() - Piece.BLOCK_HEIGHT)
				return false;
		return true;
	}
	
	public boolean isAtTheEnd(Piece P) {
		for(Rectangle i : P.getBlocks())
		{
			if(i.overlaps(borders[2]))
				return true;
			for(int j = 0; j<map.length; j++)
				for(int k=0; k<map[j].length; k++)
					if(map[j][k] != null)
						if(i.overlaps(map[j][k]))
							return true;
		}
		return false;
	}
	
	public void addPiece(Piece P) {
		for(Rectangle i : P.getBlocks())
		{
			map[(int)(i.getY())/Piece.BLOCK_HEIGHT][(int)(i.getX()-300)/Piece.BLOCK_HEIGHT] = new Block(P.getC(),i);
		}
	}
	
	public Rectangle[] getBorders() {
		return borders;
	}
	
	public Block[][] getMap() {
		return map;
	}
	
}
