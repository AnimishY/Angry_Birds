package com.mygame.angrybirds.Material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Material {
    Texture texture;
    int health;
    float x;
    float y;


    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
    }

    public void dispose() {
        texture.dispose();
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}
