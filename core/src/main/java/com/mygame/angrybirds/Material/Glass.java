package com.mygame.angrybirds.Material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Glass {
    private Texture texture;
    private int health;
    private float x, y;

    public Glass(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ui/Glass.png"));
        this.health = 50;
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
    }

    public void dispose() {
        texture.dispose();
    }
}
