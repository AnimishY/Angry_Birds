package com.mygame.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygame.angrybirds.Birds.RedB;
import com.mygame.angrybirds.Pigs.MinionPig;
import com.mygame.angrybirds.Material.Glass;

public class Level1 extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background, ground, slingshot, pauseButton;
    private Stage stage;
    private RedB redBird;
    private MinionPig minionPig;
    private Glass glass1, glass2;
    private boolean isDragging;
    private Vector2 launchStart, launchEnd, birdStartPosition;
    private Array<Vector2> trajectoryPoints;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png"));
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png"));
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png"));
        pauseButton = new Texture(Gdx.files.internal("ui/Pause.png"));

        birdStartPosition = new Vector2(75, ground.getHeight() + 22);
        redBird = new RedB(birdStartPosition.x, birdStartPosition.y);
        minionPig = new MinionPig(1120, ground.getHeight() - 10);
        glass1 = new Glass(1050, ground.getHeight() - 30);
        glass2 = new Glass(1150, ground.getHeight() - 30);

        stage = new Stage();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                //Check if the pause button is pressed
                float newWidth = 100;
                float newHeight = 100;

                if (touchPos.x >= Gdx.graphics.getWidth() - newWidth - 10 && touchPos.y >= Gdx.graphics.getHeight() - newHeight - 10) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(1));
                    return true;
                }

                if (redBird.getBounds().contains(touchPos)) {
                    isDragging = true;
                    launchStart = touchPos;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    Vector2 dragPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    redBird.setPosition(dragPos.x, dragPos.y);
                    calculateTrajectory(launchStart, dragPos);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    launchEnd = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    Vector2 launchVelocity = calculateLaunchVelocity(launchStart, launchEnd);
                    redBird.launch(launchVelocity.x, launchVelocity.y);
                    isDragging = false;
                    return true;
                }
                return false;
            }
        });

        trajectoryPoints = new Array<Vector2>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isDragging) {
            redBird.updatePosition(delta);
        }

        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < Gdx.graphics.getWidth(); i += ground.getWidth()) {
            batch.draw(ground, i, 0);
        }

        batch.draw(slingshot, 100, ground.getHeight() - 30, slingshot.getWidth() / 7, slingshot.getHeight() / 7);
        redBird.draw(batch);
        glass1.draw(batch);
        glass2.draw(batch);
        minionPig.draw(batch);

        if (isDragging) {
            for (Vector2 point : trajectoryPoints) {
                batch.draw(redBird.getTexture(), point.x, point.y, 5, 5);
            }
        }

        // Draw pause button
        float newWidth = 100;
        float newHeight = 100;
        batch.draw(pauseButton, Gdx.graphics.getWidth() - newWidth - 10, Gdx.graphics.getHeight() - newHeight - 10, newWidth, newHeight);

        batch.end();
        stage.act(delta);
        stage.draw();
    }

    private void calculateTrajectory(Vector2 start, Vector2 end) {
        trajectoryPoints.clear();
        Vector2 velocity = calculateLaunchVelocity(start, end);
        float timeStep = 0.1f;
        for (float t = 0; t < 2; t += timeStep) {
            float x = start.x + velocity.x * t;
            float y = start.y + velocity.y * t - 0.5f * 9.8f * t * t;
            trajectoryPoints.add(new Vector2(x, y));
        }
    }

    private Vector2 calculateLaunchVelocity(Vector2 start, Vector2 end) {
        float power = 5.0f;
        float dx = start.x - end.x;
        float dy = start.y - end.y;
        return new Vector2(dx * power, dy * power);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose();
        redBird.dispose();
        minionPig.dispose();
        glass1.dispose();
        glass2.dispose();
        stage.dispose();
    }
}
