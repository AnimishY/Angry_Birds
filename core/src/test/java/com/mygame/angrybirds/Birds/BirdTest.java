package com.mygame.angrybirds.Birds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BirdTest {
    @Test
    void testBirdConstructor() {
        Bird bird = new Bird();
        assertNotNull(bird);
    }

    @Test
    void birdInitialHealthIs100() {
        Bird bird = new Bird();
        assertEquals(100, bird.health);
    }

    @Test
    void birdInitialDamageIs0() {
        Bird bird = new Bird();
        assertEquals(0, bird.damage);
    }

    @Test
    void birdSetPositionUpdatesCoordinates() {
        Bird bird = new Bird();
        bird.setPosition(10, 20);
        assertEquals(10, bird.x);
        assertEquals(20, bird.y);
    }

    @Test
    void birdLaunchSetsLaunchedToTrue() {
        Bird bird = new Bird();
        bird.launch(5, 5);
        assertTrue(bird.launched);
    }

    @Test
    void birdUpdatePositionUpdatesCoordinates() {
        Bird bird = new Bird();
        bird.launch(10, 10);
        bird.updatePosition(1);
        assertNotEquals(0, bird.x);
        assertNotEquals(0, bird.y);
    }

    @Test
    void birdUpdatePositionStopsAtGround() {
        Bird bird = new Bird();
        bird.launch(0, -10);
        bird.updatePosition(1);
        assertEquals(100, bird.y);
        assertFalse(bird.launched);
    }

    @Test
    void birdTakeDamageReducesHealth() {
        Bird bird = new Bird();
        bird.takeDamage(10);
        assertEquals(90, bird.health);
    }

    @Test
    void birdIsDestroyedWhenHealthIsZero() {
        Bird bird = new Bird();
        bird.takeDamage(100);
        assertTrue(bird.isDestroyed());
    }

    @Test
    void birdIsNotDestroyedWhenHealthIsAboveZero() {
        Bird bird = new Bird();
        bird.takeDamage(50);
        assertFalse(bird.isDestroyed());
    }
}
