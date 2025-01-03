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
        float imageWidth = 1000; // Half of 500
        float imageHeight = 200; // Half of 100

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

        // create user1 button
        ImageButton user1Button = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/User1.png"))));
        user1Button.setSize(300 , 100); // Set button si
        user1Button.setPosition((screenWidth - 300) / 2, (screenHeight - (100 * 2 + 10)) / 2 + 100); // Position at the center
        user1Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("User 1 clicked!");
                // Handle user 1 click
                AngryBirdsGame.userID = 1; // Set user ID to 1
                // go back to home screen
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
            }
            return false;
        });

        stage.addActor(user1Button);


        // for button 2
        // create user2 button
        ImageButton user2Button = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/User2.png"))));
        user2Button.setSize(300 , 100); // Set button size
        user2Button.setPosition((screenWidth - 300) / 2, (screenHeight - (100 * 2 + 10)) / 2); // Position at the center
        user2Button.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("User 2 clicked!");
                // Handle user 2 click
                AngryBirdsGame.userID = 0; // Set user ID to 2
                // go back to home screen
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
            }
            return false;
        });

        stage.addActor(user2Button);
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
