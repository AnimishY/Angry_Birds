package com.mygame.angrybirds.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CorporalPig extends Pig {

    private Texture minionTexture;
    private boolean isTransformed = false;

    public CorporalPig(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/CorporalPig.png"));
        this.minionTexture = new Texture(Gdx.files.internal("ch/MinionPig.png"));
        this.health = 70;
        this.x = x;
        this.y = y;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.health < 30 && !isTransformed) {
            this.texture = minionTexture;
            isTransformed = true;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (minionTexture != null) {
            minionTexture.dispose();
        }
    }
}
