package com.mygame.angrybirds.Material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Wood extends Material{
    public Wood(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ui/Wood.png"));
        this.health = 50;
        this.x = x;
        this.y = y;
    }
}
