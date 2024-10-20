package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelEndScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background; // Background for the level end screen
    private Stage stage; // Stage for handling UI elements

    // Variable to hold the current level
    private int currentLevel;

    public LevelEndScreen(int currentLevel) {
        this.currentLevel = currentLevel; // Set the current level from which this screen is accessed
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/LevelEnd_background.png")); // Load your level end background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Create buttons
        createButtons();
    }

    private void createButtons() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float gap = 10; // Gap between buttons
        float startX = Gdx.graphics.getWidth() - buttonWidth - 20; // Right aligned
        float startY = 2; // Bottom of the screen

        // Button to go back to Level Select
        ImageButton levelSelectButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/LevelSelect_word.png"))));
        levelSelectButton.setSize(buttonWidth, buttonHeight);
        levelSelectButton.setPosition(startX, startY);

        levelSelectButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Go to Level Select clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen()); // Go to Level Select screen
                return true;
            }
            return false;
        });

        stage.addActor(levelSelectButton);

        // Button to go back to Home Screen
        ImageButton homeScreenButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/HomeScreen_word.png"))));
        homeScreenButton.setSize(buttonWidth, buttonHeight);
        homeScreenButton.setPosition(startX, startY + buttonHeight + gap);

        homeScreenButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Go to Home Screen clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage()); // Go back to Home Screen
                return true;
            }
            return false;
        });

        stage.addActor(homeScreenButton);

        // Center Button to Restart Level
        ImageButton restartLevelButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/restartLevel_button.png"))));
        restartLevelButton.setSize(buttonWidth, buttonHeight);

        float centerX = (Gdx.graphics.getWidth() - buttonWidth) / 2; // Center horizontally
        float centerY = (Gdx.graphics.getHeight() - buttonHeight)/ 10; // Center vertically

        restartLevelButton.setPosition(centerX, centerY);

        restartLevelButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Restart Level clicked!");
                switch (currentLevel) {
                    case 1:
                        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level1()); // Restart Level 1
                        break;
                    case 2:
                        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level2()); // Restart Level 2
                        break;
                    case 3:
                        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level3()); // Restart Level 3
                        break;
                    default:
                        System.out.println("Invalid Level");
                }
                return true;
            }
            return false;
        });

        stage.addActor(restartLevelButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw the background texture scaled to fit the screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.draw(background, 0, 0, screenWidth, screenHeight); // Draw at (0,0) and scale to fit

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose(); // Dispose of the stage and its resources
    }
}
