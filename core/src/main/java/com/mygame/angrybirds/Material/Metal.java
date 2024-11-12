package com.mygame.angrybirds.Material;

public class Metal {
    private int health;

    public Metal() {
        this.health = 100;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
}

