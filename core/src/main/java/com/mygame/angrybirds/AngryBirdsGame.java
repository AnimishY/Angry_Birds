package com.mygame.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ScreenUtils;

/** Main game class for Angry Birds */
public class AngryBirdsGame extends Game {
    public static int userID = 1; // User ID to keep track of the player, 1 is pranshu , 0 is animish
    private SpriteBatch batch;
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Load and play background music in a loop
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/song.mp3"));
        backgroundMusic.setLooping(true);  // Set music to loop
        backgroundMusic.play();  // Start playing the music

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
        // Dispose of the music when the game is disposed
        backgroundMusic.dispose();
        // If you have any textures loaded in screens, dispose them there instead.
    }

    // Stop music when level starts
    public void stopBackgroundMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop(); // Stop the background music
        }
    }

    // Start playing music again when needed
    public void startBackgroundMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play(); // Start playing the music
        }
    }
}
