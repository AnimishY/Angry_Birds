package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Settings extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture settingsBackground; // Background for the settings screen
    private Stage stage; // Stage for handling UI elements
    private BitmapFont font; // Font for drawing text
    private GlyphLayout layout; // Layout for text positioning
    private ImageButton backButton;

    @Override
    public void show() {
        batch = new SpriteBatch();
        settingsBackground = new Texture(Gdx.files.internal("angrybirds/background.png")); // Load your background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load font for button text
        font = new BitmapFont(); // Default LibGDX font, you can use your custom font if needed
        layout = new GlyphLayout(); // Layout for measuring text

        // Load back button texture
        Texture backButtonTexture = new Texture(Gdx.files.internal("ui/Back_button.png")); // Ensure you have a back button texture
        backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));

        // Set button size and position (Bottom left corner)
        float buttonWidth = 200;
        float buttonHeight = 100;
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(20, 20); // Bottom left corner

        // Add back button to the stage
        stage.addActor(backButton);

        // Add listener for back button click
        backButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Back clicked!");
                // Transition to HomePage
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
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

        batch.draw(settingsBackground, 0, 0, screenWidth, screenHeight); // Draw at (0,0) and scale to fit


        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }


    @Override
    public void dispose() {
        batch.dispose();
        settingsBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
        font.dispose();  // Dispose of font resource
    }
}
