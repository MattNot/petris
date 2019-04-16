package com.petris;

import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.map.Map;
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
	SpriteBatch sprite;
	Piece actual;
	Map map;
	float delay;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800,600);
		sh = new ShapeRenderer();
		createPiece();
		sprite = new SpriteBatch();
		camera.setToOrtho(true, 800, 600);
		sprite.setProjectionMatrix(camera.combined);
		sh.setProjectionMatrix(camera.combined);
		map = new Map();
		delay = 0;
		//Ci serve un playground di 400*200 i 90 e 10 sono per fare vedere meglio i bordi
	}

	@Override
	public void render () { // TODO ASSOLUTAMENTE REFACTOR HAHA
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
		 * L'idea è avere un Piece attuale che viene generato in modo casuale tra i 4 e gestire tutto attraverso
		 * il polimorfismo dei metodi. Una volta posato "buttarlo via" mettendo i suoi Rectangle dentro un 
		 * ArrayList di coppie <Rectangle, Color>.
		 */
		map.petrisControl();
		delay += Gdx.graphics.getDeltaTime();
		if(delay > 0.45f) {
			actual.move();
			delay = 0;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !map.leftCollision(actual))
			actual.moveLeft();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !map.rightCollision(actual))
			actual.moveRight();
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			map.canRotate(actual);
		}
		
		if(map.isAtTheEnd(actual)) {
			map.addPiece(actual);
			createPiece();
		}else {
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
				actual.move();
		}
		//Pezzo attuale
		sh.begin(ShapeType.Filled);
		sh.setColor(Color.BLACK);
		sh.rect(map.getBorders()[0].getX(), map.getBorders()[0].getY(), map.getBorders()[0].getWidth(), map.getBorders()[0].getHeight());
		sh.rect(map.getBorders()[1].getX(), map.getBorders()[1].getY(), map.getBorders()[1].getWidth(), map.getBorders()[1].getHeight());
		sh.rect(map.getBorders()[2].getX(), map.getBorders()[2].getY(), map.getBorders()[2].getWidth(), map.getBorders()[2].getHeight());
		sh.end();
		sprite.begin();
		//int i = 0;
		for(Rectangle r : actual.getBlocks()) { 
			//if(i!=0)
				sprite.draw(actual.getTexture(), r.getX(), r.getY());
			//else
				//sh.setColor(Color.BLACK); //DEBUG TO LET US KNOW WHO IS THE FIRST BLOCK
			//i++;
		}
		sprite.end();
		for(int j=0; j<map.getMap().length; j++) {
			for(int k=0; k<map.getMap()[j].length; k++) {
				if(map.getMap()[j][k]!=null) {
					sprite.begin();
					sprite.draw(map.getMap()[j][k].getTexture(), map.getMap()[j][k].getX(), map.getMap()[j][k].getY());
					sprite.end();
				}
				sh.begin(ShapeType.Filled);
				sh.setColor(Color.BLACK);
				sh.rect(300,0+20*j,200,1f);
				sh.rect(300+20*k,0,1f,400);
				sh.end();
			}
		}
	}
	
	private void createPiece() {
		Random r = new Random();
		int tmp = r.nextInt(7);
		switch(tmp) {
		case 0:
			actual = new PieceI("blue.png");
			break;
		case 1:
			actual = new PieceLLeft("green.png");
			break;
		case 2:
			actual = new PieceLRight("grey.png");
			break;
		case 3:
			actual = new PieceS("yellow.png");
			break;
		case 4:
			actual = new PieceT("red.png");
			break;
		case 5:
			actual = new PieceZLeft("pink.png");
			break;
		case 6:
			actual = new PieceZRight("violet.png");
			break;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//TODO Poi ci pensiamo hahah
	}
}
