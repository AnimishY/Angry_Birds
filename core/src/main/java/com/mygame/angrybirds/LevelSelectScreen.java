package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelSelectScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture levelBackground; // Background for the level selection screen
    private Stage stage; // Stage for handling UI elements

    private Texture level1Texture;
    private Texture level2Texture;
    private Texture level3Texture;
    private Texture level4Texture;
    private Texture level5Texture;

    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;
    private ImageButton level4Button;
    private ImageButton level5Button;

    @Override
    public void show() {
        batch = new SpriteBatch();
        levelBackground = new Texture(Gdx.files.internal("angrybirds/LevelSelectScreen.png")); // Load the background image

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load the back button texture
        Texture backButtonTexture = new Texture(Gdx.files.internal("ui/HomeScreen_word.png")); // Ensure you have a back button texture
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));

        // Set back button size and position (Bottom left corner)
        float backButtonWidth = 300;
        float backButtonHeight = 200;
        backButton.setSize(backButtonWidth, backButtonHeight);
        backButton.setPosition(20, 20); // Position at the bottom left corner

        // Add back button to the stage
        stage.addActor(backButton);

        // Add listener for back button click
        backButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Back clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
                return true; // Event handled
            }
            return false; // Event not handled
        });

        // Manually create level buttons with their own textures
        createLevelButtons();
    }

    private void createLevelButtons() {
        float buttonWidth = 200;
        float buttonHeight = 100;
        float gap = 10; // Gap between buttons
        float startX = (Gdx.graphics.getWidth() - (5 * buttonWidth + 4 * gap)) / 2; // Centering horizontally
        float startY = Gdx.graphics.getHeight() / 2; // Start Y position

        // Level 1 Button
        level1Texture = new Texture(Gdx.files.internal("ui/L1.png"));
        level1Button = new ImageButton(new TextureRegionDrawable(level1Texture));
        level1Button.setSize(buttonWidth, buttonHeight);
        level1Button.setPosition(startX, startY);

        level1Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level 1 selected!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level1()); // Load Level 1 screen
                return true;
            }
            return false;
        });

        stage.addActor(level1Button);

        // Level 2 Button
        level2Texture = new Texture(Gdx.files.internal("ui/L2.png"));
        level2Button = new ImageButton(new TextureRegionDrawable(level2Texture));
        level2Button.setSize(buttonWidth, buttonHeight);
        level2Button.setPosition(startX + (buttonWidth + gap), startY);

        level2Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level 2 selected!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level2());
                return true;
            }
            return false;
        });

        stage.addActor(level2Button);

        // Level 3 Button
        level3Texture = new Texture(Gdx.files.internal("ui/L3.png"));
        level3Button = new ImageButton(new TextureRegionDrawable(level3Texture));
        level3Button.setSize(buttonWidth, buttonHeight);
        level3Button.setPosition(startX + 2 * (buttonWidth + gap), startY);

        level3Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level 3 selected!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Level3());
                return true;
            }
            return false;
        });

        stage.addActor(level3Button);

        // Level 4 Button (Locked)
        level4Texture = new Texture(Gdx.files.internal("ui/L4L.png"));
        level4Button = new ImageButton(new TextureRegionDrawable(level4Texture));
        level4Button.setSize(buttonWidth, buttonHeight);
        level4Button.setPosition(startX + 3 * (buttonWidth + gap), startY);

        level4Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level 4 is Locked!");
                return true;
            }
            return false;
        });

        stage.addActor(level4Button);

        // Level 5 Button (Locked)
        level5Texture = new Texture(Gdx.files.internal("ui/L5L.png"));
        level5Button = new ImageButton(new TextureRegionDrawable(level5Texture));
        level5Button.setSize(buttonWidth, buttonHeight);
        level5Button.setPosition(startX + 4 * (buttonWidth + gap), startY);

        level5Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level 5 is Locked!");
                return true;
            }
            return false;
        });

        stage.addActor(level5Button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw the background texture scaled to fit the screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.draw(levelBackground, 0, 0, screenWidth, screenHeight); // Draw at (0,0) and scale to fit

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources

        // Dispose of individual textures
        if (level1Texture != null)
            level1Texture.dispose();
        if (level2Texture != null)
            level2Texture.dispose();
        if (level3Texture != null)
            level3Texture.dispose();
        if (level4Texture != null)
            level4Texture.dispose();
        if (level5Texture != null)
            level5Texture.dispose();
    }
}
