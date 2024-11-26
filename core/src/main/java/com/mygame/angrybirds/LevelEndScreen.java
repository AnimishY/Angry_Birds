package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LevelEndScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background; // Background for the level end screen
    private Stage stage; // Stage for handling UI elements
    private int score; // Variable to hold the score
    private BitmapFont font; // Bitmap font for displaying text
    AngryBirdsGame game = (AngryBirdsGame) Gdx.app.getApplicationListener();

    // Variable to hold the current level
    private int currentLevel;
    private boolean allPigsKilled;

    public LevelEndScreen(int currentLevel, int score, boolean allPigsKilled) {
        this.currentLevel = currentLevel; // Set the current level from which this screen is accessed
        this.score = score;

        if (allPigsKilled) {
            Level level = new Level(currentLevel, true); // true if level completed
            level.writeLevelData(currentLevel, true);

        }
    }

    @Override
    public void show() {
        game.startBackgroundMusic();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/LevelEnd_background.png")); // Load your level end background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Create buttons
        createButtons();
    }

    private void createButtons() {
        // Updated button size: Doubling the original size (400x100)
        float buttonWidth = 400;
        float buttonHeight = 100;
        float gap = 20; // Increased gap between buttons for better spacing
        float startX = Gdx.graphics.getWidth() - buttonWidth - 40; // Adjusted right-aligned positioning
        float startY = 50; // Positioned slightly higher from the bottom of the screen

        // Button to go back to Level Select
        ImageButton levelSelectButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/LevelSelect_word.png"))));
        levelSelectButton.setSize(buttonWidth, buttonHeight);
        levelSelectButton.setPosition(startX, startY-60);

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
        homeScreenButton.setPosition(startX, startY + buttonHeight + gap-60);

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
        float centerY = (Gdx.graphics.getHeight() - buttonHeight) / 10; // Center vertically

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

        // Display the score using a BitmapFont
        font = new BitmapFont();
        font.getData().setScale(5); // Increase font size
        // font color red
        font.setColor(1.0f, 0.0f, 0.0f, 1.0f);

        font.draw(batch, "Score: " + score, screenWidth / 2 - 100, screenHeight/2 -100); // Draw the score

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
