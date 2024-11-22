package com.mygame.angrybirds;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private int remainingBirds;
    private int[] pigHealths;
    private boolean[] structuresCollapsed;
    private int levelNumber;

    // Constructor
    public GameState(int remainingBirds, int[] pigHealths, boolean[] structuresCollapsed, int levelNumber) {
        this.remainingBirds = remainingBirds;
        this.pigHealths = pigHealths;
        this.structuresCollapsed = structuresCollapsed;
        this.levelNumber = levelNumber;
    }

    // Getters and Setters
    public int getRemainingBirds() {
        return remainingBirds;
    }

    public void setRemainingBirds(int remainingBirds) {
        this.remainingBirds = remainingBirds;
    }

    public int[] getPigHealths() {
        return pigHealths;
    }

    public void setPigHealths(int[] pigHealths) {
        this.pigHealths = pigHealths;
    }

    public boolean[] getStructuresCollapsed() {
        return structuresCollapsed;
    }

    public void setStructuresCollapsed(boolean[] structuresCollapsed) {
        this.structuresCollapsed = structuresCollapsed;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}
