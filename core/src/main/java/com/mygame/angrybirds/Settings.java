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
    private GlyphLayout layout; // Layout for measuring text
    private ImageButton backButton; // Back button
    private ImageButton forwardButton; // Forward button
    private float buttonHeight; // Button height

    @Override
    public void show() {
        initialize(); // Initialize components
        createButtons(); // Create and set up buttons
    }

    private void initialize() {
        batch = new SpriteBatch();
        settingsBackground = new Texture(Gdx.files.internal("angrybirds/Settings_background.png")); // Load your background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load font for button text
        font = new BitmapFont(); // Default LibGDX font, you can use your custom font if needed
        layout = new GlyphLayout(); // Layout for measuring text
    }

    private void createButtons() {
        float buttonWidth = 300;
        buttonHeight = 200; // Set button height

        // Create Back Button
        backButton = createButton("ui/HomeScreen_word.png", 20, 20, buttonWidth, buttonHeight);
        stage.addActor(backButton);
        backButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Back clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
                return true; // Event handled
            }
            return false; // Event not handled
        });

        // Create Forward Button
        forwardButton = createButton("ui/Credits_word.png", Gdx.graphics.getWidth() - buttonWidth - 20, 20, buttonWidth, buttonHeight);
        stage.addActor(forwardButton);
        forwardButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Forward clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Credits());
                return true;
            }
            return false;
        });

        // Center Buttons for User Profile and Reset Progress
        float centerX = (Gdx.graphics.getWidth() - buttonWidth) / 2; // Center horizontally
        float centerY = (3*(Gdx.graphics.getHeight() - (buttonHeight * 2 + 10))) / 4; // Center vertically

        // User Profile Button
        ImageButton userProfileButton = createButton("ui/ChangeUser_button.png", centerX, centerY, buttonWidth, buttonHeight);
        stage.addActor(userProfileButton);
        userProfileButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("User Profile clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new UserProfile());
                // Navigate to User Profile screen
                return true;
            }
            return false;
        });

        // Reset Progress Button
        ImageButton resetProgressButton = createButton("ui/ResetGame_button.png", centerX, centerY - (buttonHeight + 10), buttonWidth, buttonHeight);
        stage.addActor(resetProgressButton);
        resetProgressButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Reset Progress clicked!");
                resetGameProgress(); // Reset game progress and navigate to splash screen or home screen
                return true;
            }
            return false;
        });
    }

    private ImageButton createButton(String texturePath, float x, float y, float width, float height) {
        Texture buttonTexture = new Texture(Gdx.files.internal(texturePath));
        ImageButton button = new ImageButton(new TextureRegionDrawable(buttonTexture));
        button.setSize(width, height);
        button.setPosition(x, y);
        return button;
    }

    private void resetGameProgress() {
        System.out.println("Game progress reset.");
        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new SplashScreen()); // Navigate to splash screen before going to home screen or level select.
        // Implement actual reset logic here (e.g., clearing saved data)
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.draw(settingsBackground, 0, 0, screenWidth, screenHeight);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        settingsBackground.dispose();
        stage.dispose();
        font.dispose();
    }
}
