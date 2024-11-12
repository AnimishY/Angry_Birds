package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.angrybirds.Physics.AimDetail;

public class RedB {
    private Texture texture;
    private int health, damage;
    private float x, y;
    private AimDetail aimDetail;
    private boolean launched;

    public RedB(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/Red.png"));
        this.health = 100;
        this.damage = 50;
        this.x = x;
        this.y = y;
        this.aimDetail = new AimDetail(300, 500); // Set initial velocity
        this.launched = false;
    }

    public void launch(float velocityX, float velocityY) {
        aimDetail.setVelocity(velocityX, velocityY);
        this.launched = true;
    }

    public void updatePosition(float deltaTime) {
        if (launched) {
            x += aimDetail.getVelocityX() * deltaTime;
            y += aimDetail.getVelocityY() * deltaTime;
            aimDetail.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * 0.035f, texture.getHeight() * 0.035f);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * 0.035f, texture.getHeight() * 0.035f);
    }

    public int getDamage() {
        return damage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void dispose() {
        texture.dispose();
    }
}
