package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygame.angrybirds.Physics.AimDetail;

public class TerrenceB extends Bird{

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
