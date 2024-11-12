package com.mygame.angrybirds.Physics;

public class AimDetail {
    private float velocityX, velocityY;
    private boolean launched;

    public AimDetail(float initialVelocityX, float initialVelocityY) {
        this.velocityX = initialVelocityX;
        this.velocityY = initialVelocityY;
        this.launched = false;
    }

    public void launch() {
        this.launched = true;
    }

    public boolean isLaunched() {
        return launched;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void update(float deltaTime) {
        if (launched) {
            // Apply gravity to vertical velocity
            velocityY -= 9.8 * deltaTime;
        }
    }

    // Sets new velocities for both x and y directions
    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
