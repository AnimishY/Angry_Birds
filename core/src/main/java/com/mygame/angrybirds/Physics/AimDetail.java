package com.mygame.angrybirds.Physics;

import com.badlogic.gdx.math.Vector2;

public class AimDetail {
    private float velocityX, velocityY;
    private float initialX, initialY;
    private float time;
    private static final float GRAVITY = 50f;  // Increased gravity for more noticeable effect (cm/s²)

    public AimDetail(float initialVelocityX, float initialVelocityY) {
        this.velocityX = initialVelocityX;
        this.velocityY = initialVelocityY;
        this.time = 0;
    }

    public void update(float deltaTime) {
        // Accumulate time
        time += deltaTime;
    }

    public Vector2 getPosition(float deltaTime) {
        // Calculate current horizontal position
        float x = initialX + velocityX * time;

        // Calculate vertical position with gravity effect
        // y = initial_height + initial_vertical_velocity * time - 0.5 * gravity * time^2
        float y = initialY + velocityY * time - 0.5f * GRAVITY * time * time;

        return new Vector2(x, y);
    }

    public void setInitialPosition(float x, float y) {
        this.initialX = x;
        this.initialY = y;
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.time = 0;  // Reset time when velocity changes
    }

    public Vector2 getVelocity() {
        return new Vector2(velocityX, velocityY - GRAVITY * time);
    }
}
