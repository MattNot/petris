package com.petris;

import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;
import com.map.Map;
import com.petris.pieces.Piece;
import com.petris.pieces.PieceI;
import com.petris.pieces.PieceLRight;
import com.petris.pieces.PieceLLeft;
import com.petris.pieces.PieceS;
import com.petris.pieces.PieceZLeft;
import com.petris.pieces.PieceZRight;
import com.sound.Noise;
import com.petris.pieces.PieceT;
public class Petris extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    ShapeRenderer sh;
    FreeTypeFontGenerator generator;
    FreeTypeFontParameter parameter;
    BitmapFont font;
    Noise sound;
    SpriteBatch sprite;
    Piece actual;
    Piece hold;
    Queue<Piece> nextPieces;
    Map map;
    float delay;
    float endDelay;
    boolean started;
    boolean pause;
    Integer points;
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(800, 500);
        sh = new ShapeRenderer();
        sprite = new SpriteBatch();
        map = new Map();
        sound = new Noise();
        nextPieces = new Queue<Piece>();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));
        parameter = new FreeTypeFontParameter();
        font = new BitmapFont();
        createPiece();
        camera.setToOrtho(true, 800, 500);
        sprite.setProjectionMatrix(camera.combined);
        sh.setProjectionMatrix(camera.combined);
        delay = 0;
        points = 0;
        endDelay = 0;
        sound.playStart();
        sound.playMusic();
        pause = false;
        //Ci serve un playground di 400*200 i 90 e 10 sono per fare vedere meglio i bordi
    }

    public void exchangeWithHold(){
        if(hold == null){
            hold = actual;
            hold.goInHold();
            createPiece();
        }else{
            Piece tmp = actual;
            actual = hold;
            hold = tmp;
            hold.goInHold();
            actual.goToStart();
        }
    }

    public void play() {
        if (map.isAtTheEnd(actual) && started) {
            sound.stopMusic();
            sound.playGameover();
            pause = true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
            exchangeWithHold();
        }
        map.petrisControl(points); //TODO: Non funziona il passaggio per parametri, tha fuck?
        delay += Gdx.graphics.getDeltaTime();
        if (delay > 0.45f && !map.isAtTheEnd(actual)) {
            actual.move();
            delay = 0;
            started = false;
        }
        if (!map.leftCollision(actual) && Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            actual.moveLeft();
        if (!map.rightCollision(actual) && Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            actual.moveRight();
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !map.isAtTheEnd(actual)) {
            if (map.canRotate(actual))
                sound.playRotate();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            sound.stopMusic();
            sound.playPause();
            pause = true;
        }

        if (map.isAtTheEnd(actual)) {
            endDelay += Gdx.graphics.getDeltaTime();
            if (endDelay > 0.6) {
                map.addPiece(actual);
                points+=100;
                createPiece();
                endDelay = 0;
                sprite.setColor(Color.WHITE);
            }
        } else {
            endDelay = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                actual.move();
                points+=10;
            }
        }
    }

    public void drawBorders() {
        sh.begin(ShapeType.Filled);
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
        font.draw(sprite, Integer.toString(points), 800/2, 10);
        sprite.end();
    }

    public void drawActualPiece(){
        sprite.begin();
        for (Rectangle r : actual.getBlocks()) {
            if (endDelay > 0)
                sprite.setColor(1, 1, 1, (float) Math.abs(Math.sin(endDelay))); // We <3 Math
            sprite.draw(actual.getTexture(), r.getX(), r.getY());
        }
        sprite.setColor(Color.WHITE);
        int i = 0;
        for(Piece p : nextPieces) {
            for (Rectangle r :p.getBlocks()) {
                sprite.draw(p.getTexture(), r.getX() + 200, r.getY() + i*90);
            }
            i++;
        }
        if(hold != null) {
            for (Rectangle r : hold.getBlocks()) {
                sprite.draw(hold.getTexture(), r.getX(), r.getY());
            }
        }
        sprite.end();
    }

    public void drawMap(){
        for (int j = 0; j < map.getMap().length; j++) {
            for (int k = 0; k < map.getMap()[j].length; k++) {
                if (map.getMap()[j][k] != null) {
                    sprite.begin();
                    sprite.draw(map.getMap()[j][k].getTexture(), map.getMap()[j][k].getX(),
                            map.getMap()[j][k].getY());
                    sprite.end();
                }
                sh.begin(ShapeType.Filled);
                sh.setColor(Color.BLACK);
                sh.rect(Map.START_X + 10, Map.START_Y + Piece.BLOCK_HEIGHT * j, 200, 1f);
                sh.rect(Map.START_X + 10 + Piece.BLOCK_HEIGHT * k, Map.START_Y, 1f, 400);
                sh.end();
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!pause) {
            play();
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                sound.playPause();
                sound.playMusic();
                pause = false;
            }
        }
        this.drawBorders();
        this.drawActualPiece();
        sprite.setColor(Color.WHITE); //Undo transparency if needed
        this.drawMap();
    }

    private Piece chooseAPiece(){
        Random r = new Random();
        int tmp = r.nextInt(7);
        switch (tmp) {
            case 0:
                return new PieceI("blue.png");
            case 1:
                return new PieceLLeft("green.png");
            case 2:
                return new PieceLRight("grey.png");
            case 3:
                return new PieceS("yellow.png");
            case 4:
                return new PieceT("red.png");
            case 5:
                return (new PieceZLeft("pink.png"));
            case 6:
                return (new PieceZRight("violet.png"));
        }
        return new PieceI("blue.png");
    }

    private void createPiece() {
        started = true;
        nextPieces.addLast(chooseAPiece());
        actual = nextPieces.first();
        nextPieces.removeFirst();
        while(nextPieces.size < 4)
            nextPieces.addLast(chooseAPiece());
    }

    @Override
    public void dispose() {
        batch.dispose();
        sound.dispose();
        //TODO Poi ci pensiamo hahah
    }
}
