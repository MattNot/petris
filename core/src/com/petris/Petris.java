package com.petris;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.petris.pieces.Block;
import com.petris.pieces.Piece;
import com.petris.pieces.PieceI;
import com.petris.pieces.PieceLRight;
import com.petris.pieces.PieceLLeft;
import com.petris.pieces.PieceS;
import com.petris.pieces.PieceZLeft;
import com.petris.pieces.PieceZRight;
import com.petris.pieces.PieceT;;

public class Petris extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer sh;
	OrthographicCamera camera;
	Rectangle[] borders;
	Texture img;
	Piece actual;
	Block map[][];
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800,600);
		sh = new ShapeRenderer();
		actual = new PieceI(Color.BLUE);
		camera.setToOrtho(true, 800, 600);
		sh.setProjectionMatrix(camera.combined);
		borders = new Rectangle[3];

		//Ci serve un playground di 400*200 i 90 e 10 sono per fare vedere meglio i bordi
		borders[0] = new Rectangle(200+90, 0, 10, 400);
		borders[1] = new Rectangle(400+100, 0, 10, 410);
		borders[2] = new Rectangle(200+90, 400, 210,10);
		map = new Block[20][10];
	}

	@Override
	public void render () { // TODO ASSOLUTAMENTE REFACTOR HAHA
		boolean canMove = true;
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
		 * L'idea è avere un Piece attuale che viene generato in modo casuale tra i 4 e gestire tutto attraverso
		 * il polimorfismo dei metodi. Una volta posato "buttarlo via" mettendo i suoi Rectangle dentro un 
		 * ArrayList di coppie <Rectangle, Color>.
		 */
		for(Rectangle i : actual.getBlocks())
		{
			if(i.overlaps(borders[2])) {
				canMove = false;
				break;
			}
			for(int j = 0; j<map.length; j++) {
				for(int k=0; k<map[j].length; k++) {
					if(map[j][k] != null) {
						if(i.overlaps(map[j][k]))
						{
							canMove = false; 
							break;
						}
					}
				}
			}
		}
		if(canMove) {
			actual.move(Gdx.graphics.getDeltaTime()*3);
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
				actual.move(Gdx.graphics.getDeltaTime()*3);
			if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
				actual.moveLeft(Gdx.graphics.getDeltaTime());
			if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
				actual.moveRight(Gdx.graphics.getDeltaTime());
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
				actual.rotate(); //E' così nel gioco originale
		}else {
			for(Rectangle i : actual.getBlocks())
			{
				map[(int)(i.getY())/20][(int)(i.getX()-300)/20] = new Block(actual.getC(),i);
			}
			Random r = new Random();
			int tmp = r.nextInt(7);
			switch(tmp) {
			case 0:
				actual = new PieceI(Color.BLUE);
				break;
			case 1:
				actual = new PieceLLeft(Color.FOREST);
				break;
			case 2:
				actual = new PieceLRight(Color.CHARTREUSE);
				break;
			case 3:
				actual = new PieceS(Color.CYAN);
				break;
			case 4:
				actual = new PieceT(Color.PINK);
				break;
			case 5:
				actual = new PieceZLeft(Color.ORANGE);
				break;
			case 6:
				actual = new PieceZRight(Color.BROWN);
				break;
			}
		}
		//Pezzo attuale
		sh.begin(ShapeType.Filled);
		sh.setColor(Color.BLACK);
		sh.rect(borders[0].x, borders[0].y, borders[0].width, borders[0].height);
		sh.rect(borders[1].x, borders[1].y, borders[1].width, borders[1].height);
		sh.rect(borders[2].x, borders[2].y, borders[2].width, borders[2].height);
		int i = 0;
		for(Rectangle r : actual.getBlocks()) { 
			if(i!=0)
				sh.setColor(actual.getC());
			else
				sh.setColor(Color.BLACK); //DEBUG TO LET US KNOW WHO IS THE FIRST BLOCK
			i++;
			sh.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		sh.end();
		
		sh.begin(ShapeType.Filled);
		for(int j=0; j<map.length; j++) {
			for(int k=0; k<map[j].length; k++) {
				if(map[j][k]!=null) {
					sh.setColor(map[j][k].getC());
					sh.rect(map[j][k].x, map[j][k].y, map[j][k].width, map[j][k].height);
				}
				sh.setColor(Color.BLACK);
				sh.rect(300,0+20*j,200,1f);
				sh.rect(300+20*k,0,1f,400);
			}
		}
		sh.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//TODO Poi ci pensiamo hahah
	}
}
