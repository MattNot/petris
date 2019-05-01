package com.managers.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.map.Map;
import com.petris.pieces.Piece;

public class GraphicManager {
    OrthographicCamera camera;
    ShapeRenderer sh;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    SpriteBatch sprite;

    public GraphicManager() {
        camera = new OrthographicCamera(800, 500);
        sh = new ShapeRenderer();
        sprite = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = new BitmapFont();
        camera.setToOrtho(true, 800, 500);
        sprite.setProjectionMatrix(camera.combined);
        sh.setProjectionMatrix(camera.combined);
    }

    public void drawBorders(Map map, Integer points) {
        sh.begin(ShapeRenderer.ShapeType.Filled);
        sh.setColor(Color.BLACK);
        for (Rectangle i : map.getBorders()) {
            sh.rect(i.getX(), i.getY(), i.getWidth(), i.getHeight());
        }
        sh.end();
        parameter.size = 48;
        parameter.color = Color.BLACK;
        parameter.flip = true;
        sprite.begin();
        font = generator.generateFont(parameter);
        font.draw(sprite, Integer.toString(points), 0, 10f,0,Integer.toString(points).length(),800,Align.center, false, null);
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
                sprite.draw(p.getTexture(), r.getX() + 200, r.getY() + (i * 85));
            }
            i++;
        }
        sprite.end();
    }

    private void drawHold(Piece hold) {
        sprite.begin();
        if (hold != null) {
            for (Rectangle r : hold.getBlocks()) {
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
