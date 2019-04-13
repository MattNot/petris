package com.petris;

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
	Texture img;
	Piece l;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800,600);
		sh = new ShapeRenderer();
		l = new PieceLLeft(Color.BLUE);
		camera.setToOrtho(true, 800, 600);
		sh.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/*
		 * L'idea è avere un Piece attuale che viene generato in modo casuale tra i 4 e gestire tutto attraverso
		 * il polimorfismo dei metodi. Una volta posato "buttarlo via" mettendo i suoi Rectangle dentro un 
		 * ArrayList di coppie <Rectangle, Color>.
		 */
		
		if(l.getBlocks()[3].getY()<600-Piece.BLOCK_HEIGHT) //TODO facciamo un metodo astratto per sapere la Y in basso?
			l.move(Gdx.graphics.getDeltaTime()*3);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && l.getBlocks()[3].getY()<600-Piece.BLOCK_HEIGHT)
			l.move(Gdx.graphics.getDeltaTime()*10);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
			l.moveLeft(Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
			l.moveRight(Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			l.rotate(); //E' così nel gioco originale
		//Pezzo attuale
		sh.begin(ShapeType.Filled);
		int i = 0;
		for(Rectangle r : l.getBlocks()) { 
			if(i!=0)
				sh.setColor(l.getC());
			else
				sh.setColor(Color.BLACK); //DEBUG TO LET US KNOW WHO IS THE FIRST BLOCK
			i++;
			sh.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		sh.end();
		
		//ArrayList dei pezzi posati
		sh.begin(ShapeType.Filled);
		//TODO Disegnare i pezzi posati
		sh.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//TODO Poi ci pensiamo hahah
	}
}
