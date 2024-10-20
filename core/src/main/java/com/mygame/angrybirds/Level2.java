package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Level2 extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture ground;
    private Texture slingshot;
    private Stage stage;
    private ImageButton pauseButton;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png"));
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png"));
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture pauseButtonTexture = new Texture(Gdx.files.internal("ui/Pause.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseButtonTexture));

        float buttonWidth = 50;
        float buttonHeight = 50;

        pauseButton.setSize(buttonWidth, buttonHeight);
        pauseButton.setPosition(Gdx.graphics.getWidth() - buttonWidth - 20, Gdx.graphics.getHeight() - buttonHeight - 20);

        stage.addActor(pauseButton);

        pauseButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Pause clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(2));
                return true;
            }
            return false;
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.draw(ground, 0, 0);

        float slingshotXPosition = (screenWidth - slingshot.getWidth() / 10) / 7;
        float slingshotYPosition = ground.getHeight() - 30;

        float slingshotWidth = slingshot.getWidth() / 7;
        float slingshotHeight = slingshot.getHeight() / 7;

        batch.draw(slingshot, slingshotXPosition, slingshotYPosition, slingshotWidth, slingshotHeight);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose();
        stage.dispose();
    }
}
