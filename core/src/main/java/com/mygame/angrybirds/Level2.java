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
    private Texture wood;
    private Texture pig;
    private Texture redBird;  // Red bird texture
    private Texture chuckBird;  // Chuck bird texture
    private Stage stage;
    private ImageButton pauseButton;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angrybirds/GameBG.png"));
        ground = new Texture(Gdx.files.internal("angrybirds/ground.png"));
        slingshot = new Texture(Gdx.files.internal("angrybirds/slingshot.png"));
        wood = new Texture(Gdx.files.internal("ui/Wood.png"));
        pig = new Texture(Gdx.files.internal("ch/CorporalPig.png"));
        redBird = new Texture(Gdx.files.internal("ch/Red.png")); // Load Red bird
        chuckBird = new Texture(Gdx.files.internal("ch/Chuck.png")); // Load Chuck bird

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

        // Draw background and ground
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.draw(ground, 0, 0, screenWidth, ground.getHeight());

        // Draw slingshot
        float slingshotXPosition = (screenWidth - slingshot.getWidth() / 10) / 5;
        float slingshotYPosition = ground.getHeight() - 30;

        float slingshotWidth = slingshot.getWidth() / 7;
        float slingshotHeight = slingshot.getHeight() / 7;

        batch.draw(slingshot, slingshotXPosition, slingshotYPosition, slingshotWidth, slingshotHeight);

        // Draw Red Bird (scaled down)
        float redBirdWidth = redBird.getWidth() * 0.035f; // Scale down the Red Bird by 15%
        float redBirdHeight = redBird.getHeight() * 0.035f;
        float redBirdX = slingshotXPosition - redBirdWidth - 10; // Position Red Bird to the left of slingshot
        float redBirdY = slingshotYPosition ;

        batch.draw(redBird, redBirdX, redBirdY, redBirdWidth, redBirdHeight);

        // Draw Chuck Bird (scaled down)
        float chuckBirdWidth = chuckBird.getWidth() * 0.085f; // Scale down Chuck Bird
        float chuckBirdHeight = chuckBird.getHeight() * 0.085f;
        float chuckBirdX = redBirdX - chuckBirdWidth + 10; // Position Chuck to the left of Red Bird
        float chuckBirdY = redBirdY;

        batch.draw(chuckBird, chuckBirdX, chuckBirdY, chuckBirdWidth, chuckBirdHeight);

        // Wood dimensions
        float woodWidth = wood.getWidth() / 16.5f;
        float woodHeight = wood.getHeight() / 16.5f;

        // Adjust the wood position to be lower than the pigs
        float wood1X = screenWidth - woodWidth * 2 - 20;
        float wood1Y = ground.getHeight() - 30;

        float wood2X = wood1X + woodWidth;
        float wood2Y = wood1Y;

        float wood3X = wood2X;
        float wood3Y = wood2Y + woodHeight;

        // Place the pigs on the wood
        float pigWidth = pig.getWidth() / 12.0f;
        float pigHeight = pig.getHeight() / 12.0f;

        // Adjust wood position below the first pig
        float pig1X = wood1X + (woodWidth - pigWidth) / 2;
        float pig1Y = wood1Y + woodHeight;

        batch.draw(wood, wood1X, wood1Y, woodWidth, woodHeight); // Below pig 1
        batch.draw(wood, wood2X, wood2Y, woodWidth, woodHeight); // Horizontal wood 2
        batch.draw(wood, wood3X, wood3Y, woodWidth, woodHeight); // Vertical wood

        // Draw first pig
        batch.draw(pig, pig1X, pig1Y, pigWidth, pigHeight);

        // Adjust wood position below the second pig
        float pig2X = wood3X + (woodWidth - pigWidth) / 2;
        float pig2Y = wood3Y + woodHeight;

        // Draw second pig on top wood
        batch.draw(pig, pig2X, pig2Y, pigWidth, pigHeight);

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
        wood.dispose();
        pig.dispose();
        redBird.dispose();  // Dispose Red bird texture
        chuckBird.dispose();  // Dispose Chuck bird texture
        stage.dispose();
    }
}
