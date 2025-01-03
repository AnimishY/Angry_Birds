package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture gameBackground;

    @Override
    public void show() {
        batch = new SpriteBatch();
        gameBackground = new Texture(Gdx.files.internal("angrybirds/backmenu.jpg")); // Load your game background image.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(gameBackground, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameBackground.dispose();
    }
}
