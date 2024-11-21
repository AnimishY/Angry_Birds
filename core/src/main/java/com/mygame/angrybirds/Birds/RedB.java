package com.mygame.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygame.angrybirds.Physics.AimDetail;

public class RedB  extends Bird{

    public RedB(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("ch/Red.png"));
        this.health = 100;
        this.damage = 50;
        this.x = x;
        this.y = y;
        this.aimDetail = new AimDetail(300, 300);
        this.launched = false;
    }
}
