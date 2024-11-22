package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
// import bitmap


public class PauseScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture pauseBackground; // Background for the pause screen
    private Stage stage; // Stage for handling UI elements
    private int currentLevel; // Variable to hold the current level

    // create a font

    public PauseScreen(int currentLevel) {
        this.currentLevel = currentLevel; // Set the current level from which this screen is accessed
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        pauseBackground = new Texture(Gdx.files.internal("angrybirds/PauseScreen_background.png")); // Load your pause background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Create buttons
        createButtons();
    }

    private void createButtons() {
        // Double the original width and height
        float buttonWidth = 400; // Original width (200) * 2
        float buttonHeight = 100; // Original height (50) * 2
        float gap = 20; // Increased gap for better spacing between buttons
        float startX = (Gdx.graphics.getWidth() - buttonWidth) / 2; // Center horizontally
        float startY = Gdx.graphics.getHeight() / 2 + (buttonHeight + gap) * 1; // Start Y position

        // Resume Button
        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/ResumeButtonforPauseScreen.png"))));
        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(startX, startY);

        resumeButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Resume clicked!");
                // Resume the level
                // based on an if-else system
                if (currentLevel == 1) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level1());
                } else if (currentLevel == 2) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level2());
                } else if (currentLevel == 3) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level3());
                } else {
                    System.out.println("Invalid level!");
                }

                // Go back to the game screen
                return true;
            }
            return false;
        });

        stage.addActor(resumeButton);

        // Save and Exit Button
        ImageButton saveExitButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/SavenExitButtonforPauseScreen.png"))));
        saveExitButton.setSize(buttonWidth, buttonHeight);
        saveExitButton.setPosition(startX, startY - (buttonHeight + gap)); // Position below resume button

        saveExitButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Save and Exit clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen()); // Go to level select screen
                return true;
            }
            return false;
        });

        stage.addActor(saveExitButton);

        // Home Button
        ImageButton homeButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/HomeScreen_word.png"))));
        homeButton.setSize(buttonWidth, buttonHeight);
        homeButton.setPosition(startX, startY - 2 * (buttonHeight + gap)); // Position below save and exit button

        homeButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Home clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage()); // Go to home screen
                return true;
            }
            return false;
        });

        stage.addActor(homeButton);

        // Level End Button
        ImageButton levelEndButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/LevelEnd_button.png")))); // Ensure you have a level end button texture
        levelEndButton.setSize(buttonWidth, buttonHeight);
        levelEndButton.setPosition(startX, startY - 3 * (buttonHeight + gap)); // Position below home button

        levelEndButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Go to Level End clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(currentLevel, 0, false)); // Pass current level to Level End screen
                return true;
            }
            return false;
        });

        stage.addActor(levelEndButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw the pause screen background
        batch.draw(pauseBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draw at (0,0) and scale to fit

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }



    @Override
    public void dispose() {
        batch.dispose();
        pauseBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
    }
}
