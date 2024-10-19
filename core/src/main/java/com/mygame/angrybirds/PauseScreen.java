// PauseScreen.java

package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture pauseBackground;

    @Override
    public void show() {
        batch = new SpriteBatch();
        pauseBackground = new Texture(Gdx.files.internal("angrybirds/backmenu.jpg")); // Load your pause background image.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(pauseBackground, 0, 0); // Draw the pause screen.
        // Draw pause options (static).
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        pauseBackground.dispose();
    }
}

// LevelEndScreen.java follows a similar structure.
