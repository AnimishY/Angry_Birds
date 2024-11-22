package com.mygame.angrybirds.Material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Metal extends Material{
    public Metal(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ui/Metal.jpg"));
        this.health = 1000;
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * 0.25f, texture.getHeight() * 0.25f);
    }
}
