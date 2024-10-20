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
    private Texture glass; // Texture for the glass object
    private Texture corporalPig; // Texture for the Corporal Pig
    private Texture redBird; // Texture for the Red Bird
    private Stage stage; // Stage for handling UI elements
    private ImageButton pauseButton; // Button for pausing the game

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png")); // Load the background image
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png")); // Load the ground image
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png")); // Load the slingshot image
        glass = new Texture(Gdx.files.internal("ui/Glass.jpg")); // Load the glass texture
        corporalPig = new Texture(Gdx.files.internal("ch/MinionPig.png")); // Load the Corporal Pig texture
        redBird = new Texture(Gdx.files.internal("ch/Red.png")); // Load the Red Bird texture

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
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(1)); // Pass current level as 1
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

        // Scale down slingshot size by a factor of 7
        float slingshotWidth = slingshot.getWidth() / 7;
        float slingshotHeight = slingshot.getHeight() / 7;

        batch.draw(slingshot, slingshotXPosition, slingshotYPosition, slingshotWidth, slingshotHeight); // Draw scaled slingshot

        // Add Glass texture twice, side by side, resized to 50%
        float glassWidth = glass.getWidth() * 0.2f; // Decrease size by 50%
        float glassHeight = glass.getHeight() * 0.2f;

        // Position the glasses towards the right side of the screen
        float glassXPosition1 = screenWidth - 2 * glassWidth - 50; // 50px padding from the right
        float glassYPosition = ground.getHeight() - 32; // Place it just above the ground

        // Draw the two glass textures, side by side
        batch.draw(glass, glassXPosition1, glassYPosition, glassWidth, glassHeight); // First glass
        batch.draw(glass, glassXPosition1 + glassWidth, glassYPosition, glassWidth, glassHeight); // Second glass

        // Decrease the size of Corporal Pig by 50%
        float corporalPigWidth = corporalPig.getWidth() * 0.08f;
        float corporalPigHeight = corporalPig.getHeight() * 0.08f;

        // Position Corporal Pig above the two glasses, at the center of their combined width
        float corporalPigXPosition = glassXPosition1 + (glassWidth) - 20; // Center on top of the two glasses
        float corporalPigYPosition = glassYPosition + glassHeight; // Place it above the glasses

        // Draw the Corporal Pig
        batch.draw(corporalPig, corporalPigXPosition, corporalPigYPosition, corporalPigWidth, corporalPigHeight);

        // Add the Red Bird texture and place it to the left of the slingshot
        float redBirdWidth = redBird.getWidth() * 0.035f; // Scale down the Red Bird by 15%
        float redBirdHeight = redBird.getHeight() * 0.035f;

        // Position the Red Bird just to the left of the slingshot
        float redBirdXPosition = slingshotXPosition - redBirdWidth - 20; // 20px padding from the slingshot
        float redBirdYPosition = slingshotYPosition; // Align with the slingshot vertically

        // Draw the Red Bird
        batch.draw(redBird, redBirdXPosition, redBirdYPosition, redBirdWidth, redBirdHeight);

        batch.end();

        stage.act(delta); // Update the stage
        stage.draw();     // Draw the stage and its actors (buttons)
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose();
        glass.dispose(); // Dispose of glass texture
        corporalPig.dispose(); // Dispose of Corporal Pig texture
        redBird.dispose(); // Dispose of Red Bird texture
        stage.dispose();     // Dispose of the stage and its resources
    }
}
