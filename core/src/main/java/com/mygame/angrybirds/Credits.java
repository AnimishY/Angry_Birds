package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Credits extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture creditsBackground; // Background for credits screen
    private ImageButton backButton; // Back button
    private Stage stage; // Stage for handling UI elements

    @Override
    public void show() {
        batch = new SpriteBatch();
        creditsBackground = new Texture(Gdx.files.internal("angrybirds/Credits_background.png")); // Load your background image

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Create and set up the back button
        backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/Setting.png"))));
        backButton.setPosition(20, 170); // Positioning at bottom left
        backButton.setSize(200, 100); // Set button size

        // Add listener for the back button click
        backButton.addListener(event -> {
            if (event.isHandled()) {
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Settings()); // Go back to settings
                return true; // Event handled
            }
            return false; // Event not handled
        });

        // Add the back button to the stage
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(creditsBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draw the credits background
        batch.end();

        stage.act(delta); // Update the stage
        stage.draw(); // Draw the stage and its actors (buttons)
    }

    @Override
    public void dispose() {
        batch.dispose();
        creditsBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
    }
}
