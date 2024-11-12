package com.mygame.angrybirds.Material;

public class Wood {
    private int health;

    public Wood() {
        this.health = 75;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
}
