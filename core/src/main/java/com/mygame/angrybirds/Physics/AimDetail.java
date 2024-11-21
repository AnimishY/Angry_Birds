package com.mygame.angrybirds.Physics;

import com.badlogic.gdx.math.Vector2;

public class AimDetail {
    private float velocityX, velocityY;
    private boolean launched;
    private float initialX, initialY;  // Store initial position
    private float time;  // Track time since launch
    private static final float GRAVITY = 90.8f;  // Gravity constant (adjusted to real-world value)

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
        // Horizontal velocity remains constant
        return velocityX;
    }

    public float getVelocityY() {
        // Vertical velocity = initial velocity - gravity * time
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

    /**
     * Calculates the position of the bird at any given time, based on the time passed and initial velocity.
     *
     * @param deltaTime The time step.
     * @return A Vector2 representing the position.
     */
    public Vector2 getPosition(float deltaTime) {
        if (!launched) return new Vector2(initialX, initialY);

        // Update time
        update(deltaTime);

        // Horizontal position: initial position + horizontal velocity * time
        float x = initialX + velocityX * time;

        // Vertical position: initial position + vertical velocity * time - 0.5 * gravity * time^2
        float y = initialY + velocityY * time + 0.5f * GRAVITY * time * time;

        return new Vector2(x, y);
    }

    /**
     * Set the initial position of the bird.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public void setInitialPosition(float x, float y) {
        this.initialX = x;
        this.initialY = y;
    }
}
