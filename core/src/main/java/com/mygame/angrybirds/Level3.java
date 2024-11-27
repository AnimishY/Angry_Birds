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
import com.mygame.angrybirds.Birds.ChuckB;
import com.mygame.angrybirds.Birds.RedB;
import com.mygame.angrybirds.Birds.TerrenceB;
import com.mygame.angrybirds.Material.Metal;
import com.mygame.angrybirds.Pigs.CorporalPig;
import com.mygame.angrybirds.Pigs.KingPig;
import com.badlogic.gdx.audio.Sound;

import java.util.Iterator;

public class Level3 extends ScreenAdapter implements GameStateManager.GameStateListener  {
    private SpriteBatch batch;
    private Texture background, ground, slingshot;
    private Stage stage;
    private ImageButton pauseButton;
    private Array<Bird> birdList;
    private Iterator<Bird> birdIterator;
    private Bird currentBird;
    private Array<KingPig> kingPigList;
    private Array<CorporalPig> corporalPigList;
    private boolean isDragging, birdLaunched;
    private Vector2 launchStart, launchEnd, birdStartPosition;
    private Array<Vector2> trajectoryPoints;
    private InputMultiplexer inputMultiplexer;
    private int Score = 0;
    private float timeElapsed = 0;
    private float levelEndDelay = -1;
    private static final float GROUND_HEIGHT = 100;
    private Metal metal1, metal2, metal3, metal4, metal5, metal6;

    private Sound levelStartSound;
    // Counts of pigs and birds
    private int PigCount = 2;
    private int BirdCount = 3;

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

        // Initialize metallic obstacles
        metal1 = new Metal(1050, GROUND_HEIGHT);
        metal2 = new Metal(1050, GROUND_HEIGHT + 20);
        metal3 = new Metal(1160, GROUND_HEIGHT);
        metal4 = new Metal(1160, GROUND_HEIGHT + 20);
        metal5 = new Metal(1160, GROUND_HEIGHT + 40);
        metal6 = new Metal(1160, GROUND_HEIGHT + 60);

        birdStartPosition = new Vector2(85, GROUND_HEIGHT + 52);

        // Initialize birds
        birdList = new Array<Bird>();
        birdList.add(new RedB(birdStartPosition.x, birdStartPosition.y));
        birdList.add(new ChuckB(birdStartPosition.x, birdStartPosition.y));
        birdList.add(new TerrenceB(birdStartPosition.x, birdStartPosition.y));

        birdIterator = birdList.iterator();
        currentBird = birdIterator.hasNext() ? birdIterator.next() : null;

        // Initialize pigs
        kingPigList = new Array<KingPig>();
        corporalPigList = new Array<CorporalPig>();
        kingPigList.add(new KingPig(1180, GROUND_HEIGHT + 102));
        corporalPigList.add(new CorporalPig(1090, GROUND_HEIGHT + 60));

        stage = new Stage();

        // Pause button setup
        Texture pauseButtonTexture = new Texture(Gdx.files.internal("ui/Pause.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseButtonTexture));
        pauseButton.setSize(50, 50);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 70);
        stage.addActor(pauseButton);

        pauseButton.addListener(event -> {
            if (event.isHandled()) {
                System.out.println("Pause clicked!");
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(3));
                return true;
            }
            return false;
        });

        InputAdapter inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (birdLaunched || currentBird == null) return false;
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
                if (BirdCount <= 0 && (!kingPigList.isEmpty() || !corporalPigList.isEmpty())) {
                    ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(3, Score, false));
                }
            }

            checkCollisions();
        }

        if (kingPigList.isEmpty() && corporalPigList.isEmpty()) {
            levelEndDelay += delta;
            if (levelEndDelay >= 2) {
                ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(3, Score, true));
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
                        ((AngryBirdsGame) Gdx.app.getApplicationListener()).setScreen(new LevelEndScreen(3, Score, false));
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

        // Draw metallic obstacles
        for (Metal metal : new Metal[]{metal1, metal2, metal3, metal4, metal5, metal6}) {
            if (metal != null) metal.draw(batch);
        }

        // Draw current bird
        if (currentBird != null) {
            currentBird.draw(batch);
        }

        // Draw pigs
        for (KingPig kingPig : kingPigList) {
            kingPig.draw(batch);
        }
        for (CorporalPig corporalPig : corporalPigList) {
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

        // Check collisions with metallic obstacles
        for (Metal metal : new Metal[]{metal1, metal2, metal3, metal4, metal5, metal6}) {
            if (metal != null && currentBird.getBounds().overlaps(metal.getBounds())) {
                metal = null; // Set to null instead of dispose() immediately
                Score += 50;
                if ((metal == metal1 || metal == metal2) && !corporalPigList.isEmpty()) {
                    corporalPigList.get(0).takeDamage(currentBird.getDamage());
                }
                if ((metal == metal3 || metal == metal4 || metal == metal5 || metal == metal6) && !kingPigList.isEmpty()) {
                    kingPigList.get(0).takeDamage(currentBird.getDamage());
                }
            }
        }

        // Check collisions with King Pigs
        Iterator<KingPig> kingPigIterator = kingPigList.iterator();
        while (kingPigIterator.hasNext()) {
            KingPig kingPig = kingPigIterator.next();
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

        Iterator<CorporalPig> corporalPigIterator = corporalPigList.iterator();
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

        // Properly dispose of metal objects
        if (metal1 != null) metal1.dispose();
        if (metal2 != null) metal2.dispose();
        if (metal3 != null) metal3.dispose();
        if (metal4 != null) metal4.dispose();
        if (metal5 != null) metal5.dispose();
        if (metal6 != null) metal6.dispose();

        for (Bird bird : birdList) bird.dispose();
        for (KingPig kingPig : kingPigList) kingPig.dispose();
        for (CorporalPig corporalPig : corporalPigList) corporalPig.dispose();
        stage.dispose();
    }

    @Override
    public void onLevelStateSaved(int levelNumber) {

    }

    @Override
    public void onLevelStateLoaded(int levelNumber) {

    }
}
