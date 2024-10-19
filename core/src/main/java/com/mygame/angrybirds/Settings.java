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
    private ImageButton backButton; // Back button
    private ImageButton forwardButton; // Forward button

    @Override
    public void show() {
        initialize(); // Initialize components
        createButtons(); // Create and set up buttons
    }

    private void initialize() {
        batch = new SpriteBatch();
        settingsBackground = new Texture(Gdx.files.internal("angrybirds/background.png")); // Load your background image.

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        // Load font for button text
        font = new BitmapFont(); // Default LibGDX font, you can use your custom font if needed
        layout = new GlyphLayout(); // Layout for measuring text
    }

    private void createButtons() {
        float buttonWidth = 200;
        float buttonHeight = 100;

        // Create Back Button
        backButton = createButton("ui/Back_button.png", 20, 20, buttonWidth, buttonHeight);
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
        forwardButton = createButton("ui/Select_button.png", Gdx.graphics.getWidth() - buttonWidth - 20, 20, buttonWidth, buttonHeight);
        stage.addActor(forwardButton);
        forwardButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Forward clicked!"); // Debug statement
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new Credits());
                return true; // Event handled
            }
            return false; // Event not handled
        });
    }

    private ImageButton createButton(String texturePath, float x, float y, float width, float height) {
        Texture buttonTexture = new Texture(Gdx.files.internal(texturePath)); // Load button texture
        ImageButton button = new ImageButton(new TextureRegionDrawable(buttonTexture));
        button.setSize(width, height);
        button.setPosition(x, y);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
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
