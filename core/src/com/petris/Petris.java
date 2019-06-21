package com.petris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Queue;
import com.managers.graphics.GraphicManager;
import com.map.Map;
import com.petris.pieces.*;
import com.managers.sound.SoundManager;

import java.util.Random;

public class Petris extends ApplicationAdapter {
    GraphicManager graphicManager;
    SoundManager soundManager;
    Piece actual;
    Piece hold;
    Queue<Piece> nextPieces;
    Map map;
    int stage;
    float difficulty;
    float delay;
    float endDelay;
    float blink;
    boolean started;
    boolean pause;
    boolean canSwap;
    Integer points;

    @Override
    public void create() {
        map = new Map();
        graphicManager = new GraphicManager();
        soundManager = new SoundManager();
        nextPieces = new Queue<Piece>();
        soundManager.playStart();
        soundManager.playMusic();
        createPiece();
        stage = 1;
        difficulty = 0.45f;
        delay = 0;
        points = 0;
        endDelay = 0;
        blink = 0;
        pause = false;
        canSwap = true;
        //Ci serve un playground di 400*200 i 90 e 10 sono per fare vedere meglio i bordi
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!pause) {
            play();
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                soundManager.playPause();
                soundManager.playMusic();
                pause = false;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        	Gdx.app.exit();
        graphicManager.drawBackground();
        graphicManager.drawBorders(map);
        graphicManager.drawPoints(points);
        graphicManager.drawPieces(actual, hold, nextPieces, endDelay);
        graphicManager.drawMap(map, blink);
        graphicManager.restoreTransparency();
    }

    public void swapWithHold() {
        if (hold == null) {
            hold = actual;
            hold.goInHold();
            createPiece();
        } else {
            Piece tmp = actual;
            actual = hold;
            hold = tmp;
            hold.goInHold();
            actual.goToStart();
        }
        canSwap = false;
    }

    public void play() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !map.leftCollision(actual))
            actual.moveLeft();
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !map.rightCollision(actual))
            actual.moveRight();
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !map.isAtTheEnd(actual)) {
            if (map.canRotate(actual))
                soundManager.playRotate();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V) && canSwap) {
            swapWithHold();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            soundManager.stopMusic();
            soundManager.playPause();
            pause = true;
        }

        points += map.petrisControl(); //TODO: Non funziona il passaggio per parametri, tha fuck?
        if (!map.rowsToDelete.isEmpty()) {
            blink += Gdx.graphics.getDeltaTime();
        }
        if (blink >= 1.0) {
            map.rowsToDelete.clear();
            blink = 0;
        }
        if (map.isAtTheEnd(actual) && started) {
            soundManager.stopMusic();
            soundManager.playGameover();
            pause = true;
        }

        delay += Gdx.graphics.getDeltaTime();
        if (delay > difficulty && !map.isAtTheEnd(actual)) {
            actual.move();
            delay = 0;
            started = false;
        }

        if (map.isAtTheEnd(actual)) {
            endDelay += Gdx.graphics.getDeltaTime();
            if (endDelay > 0.6) {
                map.addPiece(actual);
                points += 100;
                createPiece();
                endDelay = 0;
                graphicManager.restoreTransparency();
            }
        } else {
            endDelay = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                actual.move();
                points += 10;
            }
        }
        
        this.setDifficulty();
    }

    private Piece chooseAPiece() {
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
        canSwap = true;
        while (nextPieces.size < 4)
            nextPieces.addLast(chooseAPiece());
    }
    
    private void setDifficulty() {
    	if(points > 5000*stage) {
    		++stage;
    		difficulty -= 0.1f;
    		System.out.println("idsv");
    	}
    }

    @Override
    public void dispose() {
        soundManager.dispose();
        //TODO Poi ci pensiamo hahah
    }
}
