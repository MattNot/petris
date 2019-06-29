package com.managers.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.map.Map;
import com.petris.Petris;
import com.petris.pieces.*;

public class GraphicManager {
	public final static int START_HOLD_X = 100;
	public final static int FINISH_HOLD_X = 200;
	public final static int START_HOLD_Y = 50;
	public final static int FINISH_HOLD_Y = 150;

	Texture background;
	Texture playMenu;
	Texture quitMenu;
	Sprite back;
	OrthographicCamera camera;
	ShapeRenderer sh;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	BitmapFont font;
	SpriteBatch sprite;
	
	public void dispose() {
		background.dispose();
		playMenu.dispose();
		quitMenu.dispose();
		sh.dispose();
		generator.dispose();
		sprite.dispose();
	}

	public GraphicManager() {
		playMenu = new Texture(Gdx.files.internal("playMenu.png"));
		quitMenu = new Texture(Gdx.files.internal("quitMenu.png"));
		camera = new OrthographicCamera(800, 500);
		sh = new ShapeRenderer();
		sprite = new SpriteBatch();
		background = new Texture(Gdx.files.internal("background.png"));
		back = new Sprite(background);
		back.flip(false, true);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = new BitmapFont();
		camera.setToOrtho(true, 800, 500);
		sprite.setProjectionMatrix(camera.combined);
		sh.setProjectionMatrix(camera.combined);
	}
	
	public void drawMenu(int status) {
		sprite.begin();
		//sprite.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		if(status == Petris.PLAY_STATUS)
			sprite.draw(playMenu, 0, 0, 800, 500, 0, 0, playMenu.getWidth(), playMenu.getHeight(), false, true);
		else if(status == Petris.QUIT_STATUS)
			sprite.draw(quitMenu, 0, 0, 800, 500, 0, 0, playMenu.getWidth(), playMenu.getHeight(), false, true);
		sprite.end();
	}

	public void drawBackground() {
		sprite.begin();
		sprite.draw(back, 0, 0, 800, 500);
		sprite.end();
	}

	public void drawBorders(Map map) {
		sh.begin(ShapeRenderer.ShapeType.Filled);
		sh.setColor(Color.BLACK);
		for (Rectangle i : map.getBorders()) {
			sh.rect(i.getX(), i.getY(), i.getWidth(), i.getHeight());
		}
		sh.end();
	}

	public void drawPoints(Integer points) {
		parameter.size = 86;
		parameter.color = Color.BLACK;
		parameter.flip = true;
		sprite.begin();
		font = generator.generateFont(parameter);
		font.draw(sprite, Integer.toString(points), 0, 10f, 0, Integer.toString(points).length(), 800, Align.center,
				false, null);
		sprite.end();
		font.dispose();
	}
	
	public void drawRecord(String name, String points) {
		sprite.begin();
		parameter.size = 68;
		parameter.color = Color.GOLD;
		font = generator.generateFont(parameter);
		font.draw(sprite, "HIGHSCORE", 70, 375);
		font.draw(sprite, name, 100, 420);
		font.draw(sprite, points, 100, 450);
		sprite.end();
		font.dispose();
	}

	public void drawPieces(Piece actual, Piece hold, Queue<Piece> nextPieces, double endDelay) {
		this.drawActual(actual, endDelay);
		this.drawQueue(nextPieces);
		this.drawHold(hold);
	}

	private void drawActual(Piece actual, double endDelay) {
		sprite.begin();
		for (Rectangle r : actual.getBlocks()) {
			if (endDelay > 0)
				sprite.setColor(1, 1, 1, (float) Math.abs(Math.sin(endDelay))); // We <3 Math
			sprite.draw(actual.getTexture(), r.getX(), r.getY());
		}
		sprite.end();
		this.restoreTransparency();
	}

	private void drawQueue(Queue<Piece> nextPieces) {
		sprite.begin();
		int i = 0;
		for (Piece p : nextPieces) {
			for (Rectangle r : p.getBlocks()) {
				if (p instanceof PieceI) {
					sprite.draw(p.getTexture(), r.getX() + 200, r.getY() + 10 + (i * 85));
				} else if (p instanceof PieceS) {
					sprite.draw(p.getTexture(), r.getX() + 180, r.getY() + (i * 85));
				} else {
					sprite.draw(p.getTexture(), r.getX() + 190, r.getY() + (i * 85));
				}
			}
			i++;
		}
		sprite.end();
	}

	private void drawHold(Piece hold) {
		sprite.begin();
		if (hold != null) {
			for (Rectangle r : hold.getBlocks()) {
				if (hold instanceof PieceI)
					sprite.draw(hold.getTexture(), r.getX() + 10, r.getY() + 10);
				else if (hold instanceof PieceS)
					sprite.draw(hold.getTexture(), r.getX() - 10, r.getY());
				else if (hold instanceof PieceT)
					sprite.draw(hold.getTexture(), r.getX() - 20, r.getY());
				else if (hold instanceof PieceZRight || hold instanceof PieceZLeft)
					sprite.draw(hold.getTexture(), r.getX() - 20, r.getY());
				else
					sprite.draw(hold.getTexture(), r.getX(), r.getY());
			}
		}
		sprite.end();
	}

	public void drawMap(Map map, float blink) {
		boolean hasToBlink;
		for (int j = 0; j < map.getMap().length; j++) {
			hasToBlink = map.rowsToDelete.contains(j);
			for (int k = 0; k < map.getMap()[j].length; k++) {
				if (map.getMap()[j][k] != null) {
					sprite.begin();
					if (hasToBlink)
						sprite.setColor(1, 1, 1, (float) Math.abs(Math.sin(blink)));
					else
						sprite.setColor(Color.WHITE);
					sprite.draw(map.getMap()[j][k].getTexture(), map.getMap()[j][k].getX(),
							map.getMap()[j][k].getY());
					sprite.end();
				}
				sh.begin(ShapeRenderer.ShapeType.Filled);
				sh.setColor(Color.BLACK);
				sh.rect(Map.START_X + 10, Map.START_Y + Piece.BLOCK_HEIGHT * j, 200, 1f);
				sh.rect(Map.START_X + 10 + Piece.BLOCK_HEIGHT * k, Map.START_Y, 1f, 400);
				sh.end();
			}
		}
	}

	public void restoreTransparency() {
		sprite.setColor(Color.WHITE);
	}
}
