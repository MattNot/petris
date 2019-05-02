package com.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle {
    private static final long serialVersionUID = 273836992715437376L;
    Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Block(Texture texture, Rectangle r) {
        super(r);
        this.texture = texture;
    }

}
