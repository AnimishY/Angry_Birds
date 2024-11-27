package com.mygame.angrybirds;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameStateManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private int remainingBirds;
    private int[] pigHealths;
    private boolean[] structuresCollapsed;
    private int levelNumber;
    private final Map<Integer, LevelState> levelStates; // Stores state of each level

    // Constructor
    public GameStateManager() {
        this.levelStates = new HashMap<>();
    }

    // Game-wide State Management
    public void initializeGameState(int levelNumber, int remainingBirds, int[] pigHealths, boolean[] structuresCollapsed) {
        this.levelNumber = levelNumber;
        this.remainingBirds = remainingBirds;
        this.pigHealths = pigHealths;
        this.structuresCollapsed = structuresCollapsed;
    }

    // Level-Specific State Management
    public void saveLevelState(int levelNumber) {
        levelStates.put(levelNumber, new LevelState(remainingBirds, pigHealths, structuresCollapsed));
    }

    public void loadLevelState(int levelNumber) {
        LevelState state = levelStates.get(levelNumber);
        if (state != null) {
            this.remainingBirds = state.getRemainingBirds();
            this.pigHealths = state.getPigHealths();
            this.structuresCollapsed = state.getStructuresCollapsed();
        } else {
            throw new IllegalStateException("No saved state for level " + levelNumber);
        }
    }

    // Serialization and Deserialization for Game State
    public void saveGameState(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    public static GameStateManager loadGameState(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameStateManager) ois.readObject();
        }
    }

    // Getters and Setters for current level state
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

    public interface GameStateListener {
        void onLevelStateSaved(int levelNumber);
        void onLevelStateLoaded(int levelNumber);
    }

    // Inner Class for Level-Specific State
    private static class LevelState implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int remainingBirds;
        private final int[] pigHealths;
        private final boolean[] structuresCollapsed;

        public LevelState(int remainingBirds, int[] pigHealths, boolean[] structuresCollapsed) {
            this.remainingBirds = remainingBirds;
            this.pigHealths = pigHealths;
            this.structuresCollapsed = structuresCollapsed;
        }

        public int getRemainingBirds() {
            return remainingBirds;
        }

        public int[] getPigHealths() {
            return pigHealths;
        }

        public boolean[] getStructuresCollapsed() {
            return structuresCollapsed;
        }
    }
}
