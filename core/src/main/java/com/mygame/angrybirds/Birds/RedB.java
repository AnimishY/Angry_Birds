package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
        this.aimDetail = new AimDetail(300, 300);
        this.launched = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, 50, 50);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public void launch(float vx, float vy) {
        aimDetail.setVelocity(vx, vy);
        launched = true;
    }

    public void updatePosition(float deltaTime) {
        if (launched) {
            aimDetail.update(deltaTime);
            float time = deltaTime;

            // Update x position (constant velocity)
            x = calculateXPosition(deltaTime);

            // Update y position using projectile motion equation
            y = calculateYPosition(deltaTime);

            // Stop motion if it touches the ground
            if (y <= 100) {
                y = 100;
                aimDetail.setVelocity(0, 0); // Stop all motion
                launched = false; // Mark as no longer in motion
            }
        }
    }

    private float calculateXPosition(float deltaTime) {
        return x + aimDetail.getVelocity().x * deltaTime;
    }

    private float calculateYPosition(float deltaTime) {
        return y + aimDetail.getVelocity().y * deltaTime - 0.5f * 9.8f * deltaTime * deltaTime;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }
}
