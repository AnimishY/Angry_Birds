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

    private Sound pigSound; // Sound for the pig noise
    private float timeSinceLastSound = 0f; // Timer for the sound

    public Pig() {
        // Load the pig sound
        pigSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pig.mp3"));
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void update(float deltaTime) {
        // Increment the timer
        timeSinceLastSound += deltaTime;

        // Check if 5 seconds have passed and play the sound
        if (timeSinceLastSound >= 5f) {
            pigSound.play();  // Play the pig sound
            timeSinceLastSound = 0f;  // Reset the timer
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
        pigSound.dispose();  // Dispose of the pig sound
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * 0.08f, texture.getHeight() * 0.08f);
    }

    public int getHealth() {
        return this.health;
    }
}
