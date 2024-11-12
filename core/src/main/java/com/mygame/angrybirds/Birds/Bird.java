package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {
    private Texture texture;
    private float x, y;             // Position of the bird
    private float width, height;    // Size of the bird
    private float scaleFactor;      // Scale factor for resizing

    public Bird(String texturePath, float x, float y, float scaleFactor) {
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.x = x;
        this.y = y;
        this.scaleFactor = scaleFactor;

        // Calculate width and height based on the scale factor
        this.width = texture.getWidth() * scaleFactor;
        this.height = texture.getHeight() * scaleFactor;
    }

    // Getters
    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Setters for position
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Method to draw the bird using a SpriteBatch
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    // Dispose method to clean up resources
    public void dispose() {
        texture.dispose();
    }
}
