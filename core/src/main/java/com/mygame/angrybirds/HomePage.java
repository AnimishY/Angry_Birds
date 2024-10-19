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

public class HomePage extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture homeBackground; // Background for the home screen
    private Stage stage; // Stage for handling UI elements
    private BitmapFont font; // Font for button labels
    private GlyphLayout layout; // For measuring text dimensions
    private ImageButton levelSelectButton; // Level Select button
    private ImageButton settingsButton;      // Settings button with new source
    private ImageButton exitButton;          // Exit button with new source

    @Override
    public void show() {
        batch = new SpriteBatch();
        homeBackground = new Texture(Gdx.files.internal("angrybirds/backmenu.jpg")); // Load your background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load button textures
        Texture buttonTexture = new Texture(Gdx.files.internal("ui/Select_button.png"));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTexture);

        // Load new textures for settings and exit buttons
        Texture settingsButtonTexture = new Texture(Gdx.files.internal("ui/Setting.png"));
        Texture exitButtonTexture = new Texture(Gdx.files.internal("ui/Exit.png.png"));

        TextureRegionDrawable settingsButtonDrawable = new TextureRegionDrawable(settingsButtonTexture);
        TextureRegionDrawable exitButtonDrawable = new TextureRegionDrawable(exitButtonTexture);

        // Create buttons
        levelSelectButton = new ImageButton(buttonDrawable);  // Button for Level Select
        settingsButton = new ImageButton(settingsButtonDrawable); // Settings button with new image
        exitButton = new ImageButton(exitButtonDrawable);      // Exit button with new image

        // Set button sizes
        float buttonWidth = 200;
        float buttonHeight = 100;

        levelSelectButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        // Position buttons with gaps between them
        float gap = 10; // Gap between buttons
        float totalWidth = (3 * buttonWidth) + (2 * gap); // Total width of all buttons plus gaps
        float startX = (Gdx.graphics.getWidth() - totalWidth) / 2; // Centering the buttons horizontally

        levelSelectButton.setPosition(startX, Gdx.graphics.getHeight() / 2 - 200); // Move buttons down to -200
        settingsButton.setPosition(startX + buttonWidth + gap, Gdx.graphics.getHeight() / 2 - 200);
        exitButton.setPosition(startX + 2 * (buttonWidth + gap), Gdx.graphics.getHeight() / 2 - 200);

        // Add buttons to the stage
        stage.addActor(levelSelectButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);

        // Load a standard bold font (libGDX provides default fonts)
        font = new BitmapFont(); // This loads the default bitmap font
        layout = new GlyphLayout(); // For measuring text dimensions

        // Add listeners for button clicks
        levelSelectButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Level Select clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelSelectScreen());
                // Transition to Level Selection Screen here
                return true; // Event handled
            }
            return false; // Event not handled
        });

        settingsButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Settings clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Settings());
                // Transition to Settings Screen here
                return true;
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Application Exited!");
                Gdx.app.exit(); // Exit the application
                return true;
            }
            return false;
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

        batch.draw(homeBackground, 0, 0, screenWidth, screenHeight); // Draw at (0,0) and scale to fit

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)

        drawTextOnButtons(); // Call method to draw text on buttons
    }

    private void drawTextOnButtons() {
        batch.begin();

        // Get the button positions and sizes
        float levelSelectX = levelSelectButton.getX();
        float buttonHeight = levelSelectButton.getHeight(); // All buttons should have the same height
        float buttonWidth = levelSelectButton.getWidth();   // All buttons should have the same width

        // Draw text for Level Select button
        layout.setText(font, "Level Select");
        font.draw(batch, layout, levelSelectX + (buttonWidth - layout.width) / 2, levelSelectButton.getY() + (buttonHeight + layout.height) / 2);

        // No text for Settings button because it's now an image
        // No text for Exit button because it's now an image

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        homeBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
        font.dispose();   // Dispose of the font resource when done
    }
}
