package com.map;

import com.badlogic.gdx.math.Rectangle;
import com.petris.pieces.Piece;
import com.petris.pieces.PieceS;

public class Map {
	
	private Rectangle borders[];
	private Block map[][];
	public final static int START_Y = 50;
	public final static int START_X = 290;
	public final static int FINISH_X = 500;
	public final static int FINISH_Y = 450;
	
	public Map() {
		borders = new Rectangle[4];
		borders[0] = new Rectangle(Map.START_X, Map.START_Y, 10, 400);//sinistra
		borders[1] = new Rectangle(Map.FINISH_X, Map.START_Y, 10, 410);//destra
		borders[2] = new Rectangle(Map.START_X, Map.FINISH_Y, 210,10);//sotto
		borders[3] = new Rectangle(Map.START_X, Map.START_Y, 210, 5);//sopra
		map = new Block[20][10];
	}
	
	public boolean canRotate(Piece p) {
		if(p instanceof PieceS)
			return true;
		int actualState = p.getState() - 1;
		p.rotate();
		while(true) {
			if(!leftCollision(p) && !rightCollision(p))
				return true;
			if(leftCollision(p) && rightCollision(p))
				break;
			if(leftCollision(p) == true && !rightCollision(p))
				p.moveRight();
			if(!leftCollision(p) && rightCollision(p) == true)
				p.moveLeft();
		}
		p.setState(actualState);
		System.out.println(p.getState());
		p.rotate();
		return false;
	}
	
	public boolean leftCollision(Piece p) {//se c'è una collisione restituisce true
		for(Rectangle i : p.getBlocks()) {
			if(i.getX() <= borders[0].getX() + Piece.BLOCK_HEIGHT)
				return true;
			if(i.getX() < Map.FINISH_X && map[(int)(i.getY() - Map.START_Y)/Piece.BLOCK_HEIGHT][((int)(i.getX()-Map.START_X)/Piece.BLOCK_HEIGHT)-1] != null)
				return true;
		}
		return false;
	}
	
	public boolean rightCollision(Piece p){//se c'è una collisione restituisce true
		for(Rectangle i : p.getBlocks()) {
			if(i.getX() >= borders[1].getX() - Piece.BLOCK_HEIGHT)
				return true;
			if(i.getX() > Map.START_X && map[(int)(i.getY() - Map.START_Y)/Piece.BLOCK_HEIGHT][((int)(i.getX()- Map.START_X)/Piece.BLOCK_HEIGHT)+1] != null)
				return true;
		}
		return false;
	}
	
	public boolean isAtTheEnd(Piece p) {//se è alla fine restituisce true
		for(Rectangle i : p.getBlocks())
		{
			if(i.overlaps(borders[2]) || i.y >= Map.FINISH_Y-Piece.BLOCK_HEIGHT) {
				return true;
			}
			for(int j = 0; j<map.length; j++)
				for(int k=0; k<map[j].length; k++)
					if(map[j][k] != null)
						if(i.y+Piece.BLOCK_HEIGHT == map[j][k].y && i.x == map[j][k].x )
							return true;
		}
		return false;
	}
	
	public void addPiece(Piece p) {
		for(Rectangle i : p.getBlocks())
		{
			map[(int)(i.getY() - Map.START_Y)/Piece.BLOCK_HEIGHT][(int)(i.getX()- Map.START_X + 10)/Piece.BLOCK_HEIGHT] = new Block(p.getTexture(),i);
		}
	}
	
	private void deleteRow(int riga) {
		Block temp[][] = new Block[20][10];
		for(int i = map.length - 1; i > riga; --i)
			temp[i] = map[i].clone();
		for(int i = riga - 1; i >= 0; --i) {
			temp[i + 1] = map[i].clone();
			for(Block k : temp[i+1]) {
				if(k!=null)
					k.y += Piece.BLOCK_HEIGHT;
			}
		}
		map = temp;
	}
	
	public void petrisControl() {
		for(int i = 0; i < map.length; ++i) {
			boolean petris = true;
			for(int j = 0; j < map[i].length; ++j)
				if(map[i][j] == null) {
					petris = false;
					break;
				}
			if(petris)
				deleteRow(i--);
		}
	}
	
	public Rectangle[] getBorders() {
		return borders;
	}
	
	public Block[][] getMap() {
		return map;
	}
	
}
