package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Level1 extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background; // Background for the level
    private Texture ground; // Texture for the ground
    private Texture slingshot; // Texture for the slingshot
    private Stage stage; // Stage for handling UI elements
    private ImageButton pauseButton; // Button for pausing the game

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png")); // Load the background image
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png")); // Load the ground image
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png")); // Load the slingshot image

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load pause button texture
        Texture pauseButtonTexture = new Texture(Gdx.files.internal("ui/Pause.png")); // Ensure you have a pause button texture
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseButtonTexture));

        // Set button size and position (Top right corner)
        float buttonWidth = 50;
        float buttonHeight = 50;
        pauseButton.setSize(buttonWidth, buttonHeight);
        pauseButton.setPosition(Gdx.graphics.getWidth() - buttonWidth - 20, Gdx.graphics.getHeight() - buttonHeight - 20); // Position at top right corner

        // Add button to the stage
        stage.addActor(pauseButton);

        // Add listener for pause button click
        pauseButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Pause clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen());
                // Handle pause action here (e.g., show a pause menu)
                return true; // Event handled
            }
            return false; // Event not handled
        });
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

        // Draw the ground at the bottom of the screen
        batch.draw(ground, 0, 0); // Positioning at (0,0) - bottom of the screen

        // Draw the slingshot above the ground with reduced size
        float slingshotXPosition = (screenWidth - slingshot.getWidth() / 10) / 7; // Center horizontally
        float slingshotYPosition = ground.getHeight() - 30; // Position it right above the ground

        // Scale down slingshot size by a factor of 10
        float slingshotWidth = slingshot.getWidth() / 7;
        float slingshotHeight = slingshot.getHeight() / 7;

        batch.draw(slingshot, slingshotXPosition, slingshotYPosition, slingshotWidth, slingshotHeight); // Draw scaled slingshot

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose(); // Dispose of textures when done
        stage.dispose();     // Dispose of the stage and its resources
    }
}
