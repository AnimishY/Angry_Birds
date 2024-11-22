package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygame.angrybirds.Physics.AimDetail;

public class Bird {
    Texture texture;
    public int health;
    public int damage;
    public float x;
    public float y;
    public AimDetail aimDetail;
    public boolean launched;

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
        aimDetail.setInitialPosition(x, y);  // IMPORTANT: Set initial position
        aimDetail.setVelocity(vx, vy);
        launched = true;
    }

    public void updatePosition(float deltaTime) {
        if (launched) {
            aimDetail.update(deltaTime);
            Vector2 newPosition = aimDetail.getPosition(deltaTime);
            x = newPosition.x;
            y = newPosition.y;

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

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int i) {
        health -= i;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}
