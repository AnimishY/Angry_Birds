package com.mygame.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** Main game class for Angry Birds */
public class AngryBirdsGame extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Start with the Splash Screen
        setScreen(new SplashScreen()); // Pass reference of AngryBirdsGame to SplashScreen
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); // Clear the screen
        super.render(); // Call the render method of the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        // If you have any textures loaded in screens, dispose them there instead.
    }
}
