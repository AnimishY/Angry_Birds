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
import com.mygame.angrybirds.Birds.Bird;
import com.mygame.angrybirds.Birds.RedB;
import com.mygame.angrybirds.Birds.ChuckB;
import com.mygame.angrybirds.Material.Glass;
import com.mygame.angrybirds.Material.Wood;
import com.mygame.angrybirds.Pigs.CorporalPig;
import com.mygame.angrybirds.Pigs.MinionPig;

import java.io.IOException;
import java.util.Iterator;
import com.badlogic.gdx.audio.Sound;

public class Level2 extends ScreenAdapter implements GameStateManager.GameStateListener  {
    private SpriteBatch batch;
    private Texture background, ground, slingshot;
    private Stage stage;
    private ImageButton pauseButton;
    private Array<Bird> birdList;
    private Iterator<Bird> birdIterator;
    private Bird currentBird;
    private Array<CorporalPig> CorporalpigList;
    private Array<MinionPig> MinionpigList;
    private boolean isDragging, birdLaunched;
    private Vector2 launchStart, launchEnd, birdStartPosition;
    private Array<Vector2> trajectoryPoints;
    private InputMultiplexer inputMultiplexer;
    private int Score = 0;
    private float timeElapsed = 0;
    private float levelEndDelay = -1;
    private static final float GROUND_HEIGHT = 100;
    private Glass glass1;
    private Wood wood1;
    private Wood wood2;
    private Wood wood3;

    private Sound levelStartSound;
    // count of pigs and birds
    private int PigCount = 2;
    private int BirdCount = 2;

    @Override
    public void show() {
        AngryBirdsGame game = (AngryBirdsGame) Gdx.app.getApplicationListener();
        game.stopBackgroundMusic();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png"));
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png"));
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png"));

        levelStartSound = Gdx.audio.newSound(Gdx.files.internal("sounds/game.wav"));
        levelStartSound.play(0.5f); // Play the sound with 50% volume (adjust as needed)

        // Fix: Initialize wood1 and glass1 as class fields
        wood1 = new Wood(1180, GROUND_HEIGHT);
        wood2 = new Wood(1180, GROUND_HEIGHT+20);
        wood3 = new Wood(1180, GROUND_HEIGHT+40);
        glass1 = new Glass(1050, GROUND_HEIGHT);

        birdStartPosition = new Vector2(85, GROUND_HEIGHT + 52);

        // Initialize bird list with multiple birds
        birdList = new Array<Bird>();
        birdList.add(new RedB(birdStartPosition.x, birdStartPosition.y));
        birdList.add(new ChuckB(birdStartPosition.x, birdStartPosition.y));

        // Create bird iterator
        birdIterator = birdList.iterator();
        currentBird = birdIterator.hasNext() ? birdIterator.next() : null;

        // Initialize pig list
        CorporalpigList = new Array<CorporalPig>();
        CorporalpigList.add(new CorporalPig(1120, GROUND_HEIGHT + 20));

        MinionpigList = new Array<MinionPig>();
        MinionpigList.add(new MinionPig(1210, GROUND_HEIGHT + 60));

        stage = new Stage();
        Texture pauseButtonTexture = new Texture(Gdx.files.internal("ui/Pause.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseButtonTexture));
        pauseButton.setSize(50, 50);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 70);
        stage.addActor(pauseButton);

        pauseButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Pause clicked!");
                System.out.println("Game saved");
                saveGameState();

                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(2));
                return true;
            }
            return false;
        });

        InputAdapter inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (birdLaunched) return false;
                Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                if (currentBird.getBounds().contains(touchPos)) {
                    isDragging = true;
                    launchStart = touchPos;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (birdLaunched || !isDragging) return false;
                Vector2 dragPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                currentBird.setPosition(dragPos.x, dragPos.y);
                calculateTrajectory(launchStart, dragPos);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (birdLaunched || !isDragging) return false;
                launchEnd = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                Vector2 launchVelocity = calculateLaunchVelocity(launchStart, launchEnd);
                currentBird.launch(launchVelocity.x, launchVelocity.y);
                isDragging = false;
                birdLaunched = true;
                timeElapsed = 0;
                return true;
            }
        };

        inputMultiplexer = new InputMultiplexer(stage, inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        trajectoryPoints = new Array<Vector2>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isDragging && currentBird != null) {
            currentBird.updatePosition(delta);

            if (currentBird.getPosition().y <= GROUND_HEIGHT) {
                currentBird.setPosition(currentBird.getPosition().x, GROUND_HEIGHT);
                currentBird.launch(0, 0);
                BirdCount--;
                if (BirdCount <= 0 && (!CorporalpigList.isEmpty() || !MinionpigList.isEmpty())) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(2, Score, false));
                }
            }

            checkCollisions();
        }

        if (MinionpigList.isEmpty() && CorporalpigList.isEmpty()) {
            levelEndDelay += delta;
            if (levelEndDelay >= 2) {
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(2, Score, true));
            }
        }

        // Move to next bird if current bird is done
        if (birdLaunched) {
            timeElapsed += delta;
            if (timeElapsed >= 10 || (currentBird != null && currentBird.getPosition().y <= GROUND_HEIGHT)) {
                if (birdIterator.hasNext()) {
                    currentBird = birdIterator.next();
                    currentBird.setPosition(birdStartPosition.x, birdStartPosition.y);
                    birdLaunched = false;
                    timeElapsed = 0;
                } else {
                    currentBird = null;
                    if (PigCount > 0) {
                        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(2, Score, false));
                    }
                }
            }
        }


        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

// Draw ground
        for (int i = 0; i < Gdx.graphics.getWidth(); i += ground.getWidth()) {
            batch.draw(ground, i, GROUND_HEIGHT - 100, ground.getWidth(), ground.getHeight());
        }

        batch.draw(slingshot, 100, GROUND_HEIGHT, slingshot.getWidth() / 7, slingshot.getHeight() / 7);

// Draw wood and glass (check for null before drawing)
        if (wood1 != null) wood1.draw(batch);
        if (wood2 != null) wood2.draw(batch);
        if (wood3 != null) wood3.draw(batch);
        if (glass1 != null) glass1.draw(batch);

// Draw current bird
        if (currentBird != null) {
            currentBird.draw(batch);
        }

// Draw pigs
        for (MinionPig MinionPig : MinionpigList) {
            MinionPig.draw(batch);
        }
        for (CorporalPig corporalPig : CorporalpigList) {
            corporalPig.draw(batch);
        }

// Draw trajectory
        if (isDragging) {
            for (Vector2 point : trajectoryPoints) {
                batch.draw(currentBird.getTexture(), point.x, point.y, 5, 5);
            }
        }

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void checkCollisions() {
        if (currentBird == null) return;

        // Check collisions with obstacles
        if (glass1 != null && currentBird.getBounds().overlaps(glass1.getBounds())) {
            glass1.dispose();
            glass1 = null;
            Score += 25;
            CorporalpigList.get(0).takeDamage(currentBird.getDamage());
            if (CorporalpigList.get(0).isDestroyed()) {
                CorporalpigList.removeIndex(0);
                PigCount--;
                Score += 70;
            }
        }

        if (wood1 != null && currentBird.getBounds().overlaps(wood1.getBounds())) {
            wood1.dispose();
            wood1 = null;
            Score += 50;
            if (!MinionpigList.isEmpty()) {
                MinionpigList.get(0).takeDamage(50);
                if (MinionpigList.get(0).isDestroyed()) {
                    MinionpigList.removeIndex(0);
                    PigCount--;
                    Score += 50;
                }
            }
        }

        if (wood2 != null && currentBird.getBounds().overlaps(wood2.getBounds())) {
            wood2.dispose();
            wood2 = null;
            Score += 50;
            if (!MinionpigList.isEmpty()) {
                MinionpigList.get(0).takeDamage(50);
                if (MinionpigList.get(0).isDestroyed()) {
                    MinionpigList.removeIndex(0);
                    PigCount--;
                    Score += 50;
                }
            }
        }

        if (wood3 != null && currentBird.getBounds().overlaps(wood3.getBounds())) {
            wood3.dispose();
            wood3 = null;
            Score += 50;
            if (!MinionpigList.isEmpty()) {
                MinionpigList.get(0).takeDamage(50);
                if (MinionpigList.get(0).isDestroyed()) {
                    MinionpigList.removeIndex(0);
                    PigCount--;
                    Score += 50;
                }
            }
        }

        // Check collisions with pigs
        Iterator<MinionPig> kingPigIterator = MinionpigList.iterator();
        while (kingPigIterator.hasNext()) {
            MinionPig kingPig = kingPigIterator.next();
            if (currentBird.getBounds().overlaps(kingPig.getBounds())) {
                kingPig.takeDamage(currentBird.getDamage());
                if (kingPig.isDestroyed()) {
                    kingPigIterator.remove();
                    PigCount--;
                    Score += 200;
                }
                currentBird.dispose();
                currentBird = null;
                break;
            }
        }

        // Check collisions with Corporal Pigs
        if (currentBird == null) return; // Exit if bird was destroyed in previous collision

        Iterator<CorporalPig> corporalPigIterator = CorporalpigList.iterator();
        while (corporalPigIterator.hasNext()) {
            CorporalPig corporalPig = corporalPigIterator.next();
            if (currentBird.getBounds().overlaps(corporalPig.getBounds())) {
                corporalPig.takeDamage(currentBird.getDamage());
                if (corporalPig.isDestroyed()) {
                    corporalPigIterator.remove();
                    PigCount--;
                    Score += 150;
                }
                currentBird.dispose();
                currentBird = null;
                break;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        slingshot.dispose();
        stage.dispose();

        for (Bird bird : birdList) {
            bird.dispose();
        }

        for (CorporalPig pig : CorporalpigList) {
            pig.dispose();
        }

        for (MinionPig pig : MinionpigList) {
            pig.dispose();
        }

        if (wood2 != null) wood1.dispose();
        if (wood3 != null) wood1.dispose();
        if (wood1 != null) wood1.dispose();
        if (glass1 != null) glass1.dispose();
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

    public void saveGameState() {
        try {
            GameStateManager gameStateManager = new GameStateManager();
            gameStateManager.saveGameState("savedGameState.dat");
            System.out.println("Game state saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }

    @Override
    public void onLevelStateSaved(int levelNumber) {

    }

    @Override
    public void onLevelStateLoaded(int levelNumber) {

    }
}
