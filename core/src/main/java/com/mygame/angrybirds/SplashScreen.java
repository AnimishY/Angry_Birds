package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture splashImage; // Image to display on the splash screen
    private float timeElapsed; // Time elapsed since the splash screen started

    public SplashScreen() {
        batch = new SpriteBatch();
        splashImage = new Texture(Gdx.files.internal("angrybirds/splash.png")); // Load your splash image
        timeElapsed = 0; // Initialize elapsed time
    }

    @Override
    public void render(float delta) {
        timeElapsed += delta; // Increment elapsed time

        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draw the splash image
        batch.end();

        // Transition to HomePage after 5 seconds
        if (timeElapsed > 1) {
            ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new HomePage());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        splashImage.dispose(); // Dispose of the splash image when done
    }
}
