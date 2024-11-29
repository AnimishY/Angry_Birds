package com.mygame.angrybirds.Pigs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Pig {
    public Texture texture;
    public int health;
    public float x, y;

    private Sound pigSound;
    private float timeSinceLastSound = 0f;


    public void takeDamage(int damage) {
        health -= damage;
    }

    public void update(float deltaTime) {

        timeSinceLastSound += deltaTime;

        if (timeSinceLastSound >= 5f) {
            pigSound.play();
            timeSinceLastSound = 0f;
        }
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

    public int getHealth() {
        return this.health;
    }
}
