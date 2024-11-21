package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygame.angrybirds.Physics.AimDetail;

public class ChuckB  extends Bird{

    public ChuckB(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/Chuck.png"));
        this.health = 100;
        this.damage = 75;
        this.x = x;
        this.y = y;
        this.aimDetail = new AimDetail(400, 400);
        this.launched = false;
    }
}
