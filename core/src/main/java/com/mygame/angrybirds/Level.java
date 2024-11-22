package com.mygame.angrybirds;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Level {
    public int levelnumber;
    public boolean isCompleted;

    public Level(int levelnumber, boolean isCompleted) {
        this.levelnumber = levelnumber;
        this.isCompleted = isCompleted;
    }

    public void writeLevelData(int levelnumber, boolean isCompleted) {
        try {
            // Read existing data or create default data if file doesn't exist
            boolean[] levelStatuses = new boolean[5];
            try {
                // Try to read existing file
                List<String> lines = readAllLines(Paths.get("leveldata.csv"));
                for (int i = 0; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length == 2) {
                        int level = Integer.parseInt(parts[0]);
                        levelStatuses[level - 1] = Boolean.parseBoolean(parts[1]);
                    }
                }
            } catch (IOException e) {
                // If file doesn't exist, initialize with all false
                Arrays.fill(levelStatuses, false);
            }

            // Update the specific level's status
            levelStatuses[levelnumber - 1] = isCompleted;

            FileWriter writer = null;
            try {
                writer = new FileWriter("leveldata.csv");
                for (int i = 0; i < levelStatuses.length; i++) {
                    writer.append(String.valueOf(i + 1))
                        .append(",")
                        .append(String.valueOf(levelStatuses[i]))
                        .append("\n");
                }
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean readLevelData(int levelnumber) {
        // Read existing data or create default data if file doesn't exist
        boolean[] levelStatuses = new boolean[5];
        try {
            // Try to read existing file
            List<String> lines = readAllLines(Paths.get("leveldata.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 2) {
                    int level = Integer.parseInt(parts[0]);
                    levelStatuses[level - 1] = Boolean.parseBoolean(parts[1]);
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, initialize with all false
            Arrays.fill(levelStatuses, false);
        }

        // Return the status of the specific level
        return levelStatuses[levelnumber - 1];
    }

    // reset all levels to false
    public static void resetLevelData() {
        try {
            FileWriter writer = new FileWriter("leveldata.csv");
            for (int i = 0; i < 5; i++) {
                writer.append(String.valueOf(i + 1))
                    .append(",")
                    .append("false")
                    .append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
