package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UserProfile extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background; // Background for the user profile screen
    private Stage stage; // Stage for handling UI elements

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/UserProfile_background.png")); // Load your user profile background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Create buttons and images
        createUIElements();
    }

    private void createUIElements() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // New dimensions for the images (width and height reduced by a factor of 2)
        float imageWidth = 250; // Half of 500
        float imageHeight = 50; // Half of 100

        // Load two center images
        Texture image1Texture = new Texture(Gdx.files.internal("ui/User1.png")); // Ensure you have this texture
        Texture image2Texture = new Texture(Gdx.files.internal("ui/User2.png")); // Ensure you have this texture

        // Position images in the center
        float image1XPosition = (screenWidth - imageWidth) / 4;
        float image1YPosition = (screenHeight - (imageHeight * 2 + 10)) / 2 + imageHeight; // Adjust for spacing

        float image2XPosition = (screenWidth - imageWidth) / 4;
        float image2YPosition = (screenHeight - (imageHeight * 2 + 10)) / 2;

        // Create settings button and move it to the bottom left
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/Setting.png")))); // Ensure you have a settings button texture
        settingsButton.setSize(100, 100); // Size of the settings button
        settingsButton.setPosition(20, 20); // Position at bottom left corner

        settingsButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Settings clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Settings()); // Go back to settings screen
                return true;
            }
            return false;
        });

        stage.addActor(settingsButton);
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

        // Draw center images with updated width and height
        Texture image1Texture = new Texture(Gdx.files.internal("ui/User1.png"));
        Texture image2Texture = new Texture(Gdx.files.internal("ui/User2.png"));

        batch.draw(image1Texture, (screenWidth - 250) / 2, (screenHeight - (50 * 2 + 10)) / 2 + 50, 250, 50);
        batch.draw(image2Texture, (screenWidth - 250) / 2, (screenHeight - (50 * 2 + 10)) / 2, 250, 50);

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
