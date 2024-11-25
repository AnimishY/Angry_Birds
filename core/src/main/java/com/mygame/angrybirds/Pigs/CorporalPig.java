package com.mygame.angrybirds.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CorporalPig extends Pig {

    public CorporalPig(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/CorporalPig.png"));
        this.health = 70;
        this.x = x;
        this.y = y;
    }

}
