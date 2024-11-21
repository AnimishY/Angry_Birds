package com.mygame.angrybirds.Physics;

import com.badlogic.gdx.math.Vector2;

public class AimDetail {
    private float velocityX, velocityY;
    private boolean launched;
    private float initialX, initialY;  // Store initial position
    private float time;  // Track time since launch
    private static final float GRAVITY = 19.8f;  // Gravity constant

    public AimDetail(float initialVelocityX, float initialVelocityY) {
        this.velocityX = initialVelocityX;
        this.velocityY = initialVelocityY;
        this.launched = false;
        this.time = 0;
    }

    public void launch() {
        this.launched = true;
        this.time = 0;
    }

    public boolean isLaunched() {
        return launched;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        // Current vertical velocity = initial velocity - gt
        return velocityY - (GRAVITY * time);
    }

    public void update(float deltaTime) {
        if (launched) {
            time += deltaTime;
        }
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.time = 0;
    }

    public Vector2 getVelocity() {
        return new Vector2(velocityX, getVelocityY());
    }
}
