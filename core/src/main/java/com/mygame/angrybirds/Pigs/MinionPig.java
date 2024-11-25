package com.mygame.angrybirds.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MinionPig extends Pig {


    public MinionPig(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/MinionPig.png"));
        this.health = 50;
        this.x = x;
        this.y = y;
    }

}
