package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.angrybirds.Physics.AimDetail;

import java.util.Collection;

public class RedB {
    private Texture texture;
    private int health, damage;
    private float x, y;
    private float initialX, initialY;  // Store initial position
    private AimDetail aimDetail;
    private boolean launched;

    public RedB(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/Red.png"));
        this.health = 100;
        this.damage = 50;
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.aimDetail = new AimDetail(300, 500);
        this.launched = false;
    }

    public void launch(float velocityX, float velocityY) {
        aimDetail.setVelocity(velocityX, velocityY);
        this.launched = true;
        this.initialX = x;
        this.initialY = y;
        aimDetail.launch();
    }

    public void updatePosition(float deltaTime) {
        if (launched) {
            aimDetail.update(deltaTime);
            float time = deltaTime;

            // Update x position (constant velocity)
            x = calculateXPosition(deltaTime);

            // Update y position using projectile motion equation
            y = calculateYPosition(deltaTime);

            // Optional: Add ground collision check
            if (y < 0) {
                y = 0;
                launched = false;
            }
        }
    }

    private float calculateXPosition(float deltaTime) {
        // x = x0 + vx * t
        return x + aimDetail.getVelocityX() * deltaTime;
    }

    private float calculateYPosition(float deltaTime) {
        // y = y0 + vy*t - (1/2)gt^2
        float currentVY = aimDetail.getVelocityY();
        return y + (currentVY * deltaTime) - (0.5f * 9.8f * deltaTime * deltaTime);
    }

    public void dispose() {
        texture.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth()*0.035f, texture.getHeight()*0.035f);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth()*0.035f, texture.getHeight()*0.035f);
    }

    public Texture getTexture() {
        return texture;
    }
}
