package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.angrybirds.Physics.AimDetail;

public class TerrenceB extends Bird{

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, 65, 65);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 65, 65);
    }

    public TerrenceB(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/Terence.png"));
        this.health = 100;
        this.damage = 100;
        this.x = x;
        this.y = y;
        this.aimDetail = new AimDetail(250,250);
        this.launched = false;
    }
}
