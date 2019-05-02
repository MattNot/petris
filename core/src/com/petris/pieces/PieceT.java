package com.petris.pieces;

import com.badlogic.gdx.math.Rectangle;
import com.managers.graphics.GraphicManager;
import com.map.Map;

public class PieceT extends Piece {

    public PieceT(String path) {
        super(path);
        this.goToStart();
    }

    @Override
    public void rotate() {
        if (this.getState() == 0) {
            blocks[1].setX(blocks[0].getX());
            blocks[1].setY(blocks[0].getY() - Piece.BLOCK_HEIGHT);
            blocks[2].setX(blocks[0].getX());
            blocks[2].setY(blocks[0].getY() + Piece.BLOCK_HEIGHT);
            blocks[3].setX(blocks[0].getX() + Piece.BLOCK_HEIGHT);
            blocks[3].setY(blocks[0].getY());
            this.setState(1);
        } else if (this.getState() == 1) {
            blocks[1].setX(blocks[0].getX() + Piece.BLOCK_HEIGHT);
            blocks[1].setY(blocks[0].getY());
            blocks[2].setX(blocks[0].getX() - Piece.BLOCK_HEIGHT);
            blocks[2].setY(blocks[0].getY());
            blocks[3].setX(blocks[0].getX());
            blocks[3].setY(blocks[0].getY() + Piece.BLOCK_HEIGHT);
            this.setState(2);
        } else if (this.getState() == 2) {
            blocks[1].setX(blocks[0].getX());
            blocks[1].setY(blocks[0].getY() + Piece.BLOCK_HEIGHT);
            blocks[2].setX(blocks[0].getX());
            blocks[2].setY(blocks[0].getY() - Piece.BLOCK_HEIGHT);
            blocks[3].setX(blocks[0].getX() - Piece.BLOCK_HEIGHT);
            blocks[3].setY(blocks[0].getY());
            this.setState(3);
        } else if (this.getState() == 3) {
            blocks[1].setX(blocks[0].getX() - Piece.BLOCK_HEIGHT);
            blocks[1].setY(blocks[0].getY());
            blocks[2].setX(blocks[0].getX() + Piece.BLOCK_HEIGHT);
            blocks[2].setY(blocks[0].getY());
            blocks[3].setX(blocks[0].getX());
            blocks[3].setY(blocks[0].getY() - Piece.BLOCK_HEIGHT);
            this.setState(0);
        }
    }

    @Override
    public void goInHold() {
        blocks[0].setX(GraphicManager.START_HOLD_X + Piece.BLOCK_HEIGHT);
        blocks[0].setY(GraphicManager.START_HOLD_Y);
        blocks[1].setX(GraphicManager.START_HOLD_X);
        blocks[1].setY(GraphicManager.START_HOLD_Y + Piece.BLOCK_HEIGHT);
        blocks[2].setX(GraphicManager.START_HOLD_X + Piece.BLOCK_HEIGHT);
        blocks[2].setY(GraphicManager.START_HOLD_Y + Piece.BLOCK_HEIGHT);
        blocks[3].setX(GraphicManager.START_HOLD_X + Piece.BLOCK_HEIGHT * 2);
        blocks[3].setY(GraphicManager.START_HOLD_Y + Piece.BLOCK_HEIGHT);
        this.setState(0);
    }

    @Override
    public void goToStart() {
        blocks[0] = new Rectangle(800 / 2, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
        blocks[1] = new Rectangle(800 / 2 - Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT,
                Piece.BLOCK_HEIGHT);
        blocks[2] = new Rectangle(800 / 2 + Piece.BLOCK_HEIGHT, Map.START_Y + Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT,
                Piece.BLOCK_HEIGHT);
        blocks[3] = new Rectangle(800 / 2, Map.START_Y, Piece.BLOCK_HEIGHT, Piece.BLOCK_HEIGHT);
    }

}
