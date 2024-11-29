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
    private Texture homeBackground;
    private Stage stage;
    private BitmapFont font;
    private GlyphLayout layout;
    private ImageButton levelSelectButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;

    @Override
    public void show() {
        batch = new SpriteBatch();
        homeBackground = new Texture(Gdx.files.internal("angrybirds/backmenu.jpg"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage

        Texture buttonTexture = new Texture(Gdx.files.internal("ui/LevelSelect_word.png"));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTexture);

        Texture settingsButtonTexture = new Texture(Gdx.files.internal("ui/Setting.png"));
        Texture exitButtonTexture = new Texture(Gdx.files.internal("ui/Exit.png"));

        TextureRegionDrawable settingsButtonDrawable = new TextureRegionDrawable(settingsButtonTexture);
        TextureRegionDrawable exitButtonDrawable = new TextureRegionDrawable(exitButtonTexture);

        levelSelectButton = new ImageButton(buttonDrawable);  // Button for Level Select
        settingsButton = new ImageButton(settingsButtonDrawable); // Settings button with new image
        exitButton = new ImageButton(exitButtonDrawable);      // Exit button with new image

        float buttonWidth = 300;
        float buttonHeight = 200;

        levelSelectButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        float gap = 10; // Gap between buttons
        float totalWidth = (3 * buttonWidth) + (2 * gap); // Total width of all buttons plus gaps
        float startX = (Gdx.graphics.getWidth() - totalWidth) / 2; // Centering the buttons horizontally

        levelSelectButton.setPosition(startX, Gdx.graphics.getHeight() / 2 - 400); // Move buttons down to -200
        settingsButton.setPosition(startX + buttonWidth + gap, Gdx.graphics.getHeight() / 2 - 400);
        exitButton.setPosition(startX + 2 * (buttonWidth + gap), Gdx.graphics.getHeight() / 2 - 400);

        stage.addActor(levelSelectButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);

        font = new BitmapFont(); // This loads the default bitmap font
        layout = new GlyphLayout(); // For measuring text dimensions

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

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.draw(homeBackground, 0, 0, screenWidth, screenHeight); // Draw at (0,0) and scale to fit

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)

    }


    @Override
    public void dispose() {
        batch.dispose();
        homeBackground.dispose();
        stage.dispose(); // Dispose of the stage and its resources
        font.dispose();   // Dispose of the font resource when done
    }
}
