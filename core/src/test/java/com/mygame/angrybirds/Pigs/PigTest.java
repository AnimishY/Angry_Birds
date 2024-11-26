package com.mygame.angrybirds.Pigs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PigTest {
    @Test
    void pigInitialHealthIsSetCorrectly() {
        Pig pig = new Pig();
        pig.health = 100;
        assertEquals(100, pig.health);
    }

    @Test
    void pigTakeDamageReducesHealth() {
        Pig pig = new Pig();
        pig.health = 100;
        pig.takeDamage(10);
        assertEquals(90, pig.health);
    }

    @Test
    void pigIsDestroyedWhenHealthIsZero() {
        Pig pig = new Pig();
        pig.health = 100;
        pig.takeDamage(100);
        assertTrue(pig.isDestroyed());
    }

    @Test
    void pigIsNotDestroyedWhenHealthIsAboveZero() {
        Pig pig = new Pig();
        pig.health = 100;
        pig.takeDamage(50);
        assertFalse(pig.isDestroyed());
    }

}
