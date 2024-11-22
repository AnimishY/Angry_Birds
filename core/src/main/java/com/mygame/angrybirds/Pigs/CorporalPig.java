package com.mygame.angrybirds.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CorporalPig {
    private Texture texture;
    private int health;
    private float x, y;

    public CorporalPig(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/CorporalPig.png"));
        this.health = 50;
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * 0.08f, texture.getHeight() * 0.08f);
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * 0.08f, texture.getHeight() * 0.08f);

    }
}
