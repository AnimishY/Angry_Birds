package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class LevelSelectScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture levelBackground; // Background for the level selection screen
    private Stage stage; // Stage for handling UI elements
    private BitmapFont font; // Font for drawing text
    private GlyphLayout layout; // Layout for measuring text dimensions

    @Override
    public void show() {
        batch = new SpriteBatch();
        levelBackground = new Texture(Gdx.files.internal("angrybirds/background.png")); // Load your background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load font for button text
        font = new BitmapFont(); // Load your desired font here (make sure to provide a font file if needed)
        layout = new GlyphLayout(); // Initialize layout for measuring text

        // Load back button texture
        Texture backButtonTexture = new Texture(Gdx.files.internal("ui/button.png")); // Ensure you have a back button texture
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));

        // Set back button size and position
        float backButtonWidth = 100;
        float backButtonHeight = 50;
        backButton.setSize(backButtonWidth, backButtonHeight);
        backButton.setPosition(20, Gdx.graphics.getHeight() - backButtonHeight - 20); // Position at top left corner

        // Add button to the stage
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

        // Create level buttons (1-10)
        createLevelButtons();
    }

    private void createLevelButtons() {
        float buttonWidth = 100;
        float buttonHeight = 50;
        float gap = 10; // Gap between buttons
        float startX = (Gdx.graphics.getWidth() - (5 * buttonWidth + 4 * gap)) / 2; // Centering horizontally
        float startY = Gdx.graphics.getHeight() / 2; // Start Y position

        for (int i = 1; i <= 5; i++) { // Changed to 10 for levels 1-10
            ImageButton levelButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/button.png"))));
            levelButton.setSize(buttonWidth, buttonHeight);
            levelButton.setPosition(startX + ((i - 1) % 5) * (buttonWidth + gap), startY - ((i - 1) / 5) * (buttonHeight + gap)); // Arrange in rows of 5

            final int levelNumber = i; // Final variable for listener
            levelButton.addListener(event -> {
                if (event.isHandled()) {
                    System.out.println("Level " + levelNumber + " selected!");
                    // Here you would transition to the specific level screen
                    // Replace `LevelScreen` with the actual screen class for the level

                    return true; // Event handled
                }
                return false; // Event not handled
            });

            stage.addActor(levelButton);
        }
    }

    private void drawTextOnButtons() {
        batch.begin();

        // Get the button positions and sizes
        float buttonWidth = 100; // Adjust to your button size
        float buttonHeight = 50;  // Adjust to your button size
        float gap = 10; // Gap between buttons
        float startX = (Gdx.graphics.getWidth() - (5 * buttonWidth + 4 * gap)) / 2; // Centering horizontally
        float startY = Gdx.graphics.getHeight() / 2; // Start Y position

        for (int i = 1; i <= 5; i++) {
            layout.setText(font, "Level " + i);
            float x = startX + ((i - 1) % 5) * (buttonWidth + gap) + (buttonWidth - layout.width) / 2;
            float y = startY - ((i - 1) / 5) * (buttonHeight + gap) + (buttonHeight + layout.height) / 2;
            font.draw(batch, layout, x, y);
        }

        batch.end();
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

        // Draw text on buttons after drawing the stage
        drawTextOnButtons();
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
        font.dispose(); // Dispose the font resource
    }
}
