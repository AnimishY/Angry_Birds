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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygame.angrybirds.Birds.RedB;
import com.mygame.angrybirds.Pigs.MinionPig;
import com.mygame.angrybirds.Material.Glass;

public class Level1 extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background, ground, slingshot;
    private ImageButton pauseButton;
    private Stage stage;
    private RedB redBird;
    private MinionPig minionPig;
    private Glass glass1, glass2;
    private boolean isDragging, birdLaunched;
    private Vector2 launchStart, launchEnd, birdStartPosition;
    private Array<Vector2> trajectoryPoints;
    private InputMultiplexer inputMultiplexer;

    private int PigCount = 1;
    private int BirdCount = 1;
    private int Score = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png"));
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png"));
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png"));

        // Initialize bird, pigs, and materials
        birdStartPosition = new Vector2(75, ground.getHeight() + 22);
        redBird = new RedB(birdStartPosition.x, birdStartPosition.y);
        minionPig = new MinionPig(1120, ground.getHeight() - 10);
        glass1 = new Glass(1050, ground.getHeight() - 30);
        glass2 = new Glass(1150, ground.getHeight() - 30);

        stage = new Stage();

        // Set up the pause button
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

        // Custom input processor for bird dragging and launching
        InputAdapter inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (birdLaunched) return false; // Prevent further interaction if the bird has been launched

                Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                if (redBird.getBounds().contains(touchPos)) {
                    isDragging = true;
                    launchStart = touchPos;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (birdLaunched || !isDragging) return false; // Prevent dragging after launch

                Vector2 dragPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                redBird.setPosition(dragPos.x, dragPos.y);
                calculateTrajectory(launchStart, dragPos);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (birdLaunched || !isDragging) return false; // Prevent launching after the bird is launched

                launchEnd = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                Vector2 launchVelocity = calculateLaunchVelocity(launchStart, launchEnd);
                redBird.launch(launchVelocity.x, launchVelocity.y);
                isDragging = false;
                birdLaunched = true; // Mark the bird as launched
                return true;
            }
        };

        // Combine input processors
        inputMultiplexer = new InputMultiplexer(stage, inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        trajectoryPoints = new Array<>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isDragging && redBird != null) {
            redBird.updatePosition(delta);
            checkCollisions();
        }

        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < Gdx.graphics.getWidth(); i += ground.getWidth()) {
            batch.draw(ground, i, 0);
        }

        batch.draw(slingshot, 100, ground.getHeight() - 30, slingshot.getWidth() / 7, slingshot.getHeight() / 7);
        if (redBird != null) redBird.draw(batch);
        if (glass1 != null) glass1.draw(batch);
        if (glass2 != null) glass2.draw(batch);
        if (minionPig != null) minionPig.draw(batch);

        if (isDragging) {
            for (Vector2 point : trajectoryPoints) {
                batch.draw(redBird.getTexture(), point.x, point.y, 5, 5);
            }
        }

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

    private void checkCollisions() {
        assert redBird != null;
        if (redBird.getBounds().overlaps(minionPig.getBounds())) {
            redBird.dispose();
            minionPig.dispose();
            redBird = null;
            minionPig = null;
            PigCount--;
            Score += 100;

            if (PigCount == 0) {
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(1, Score));
            }
        }

        if (redBird != null && redBird.getBounds().overlaps(glass1.getBounds())) {
            redBird.dispose();
            glass1.dispose();
            redBird = null;
            glass1 = null;
            Score += 25;
        }

        if (redBird != null && redBird.getBounds().overlaps(glass2.getBounds())) {
            redBird.dispose();
            glass2.dispose();
            redBird = null;
            glass2 = null;
            Score += 25;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose();
        stage.dispose();
        if (redBird != null) redBird.dispose();
        if (minionPig != null) minionPig.dispose();
        if (glass1 != null) glass1.dispose();
        if (glass2 != null) glass2.dispose();
    }
}
