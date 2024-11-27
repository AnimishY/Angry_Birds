package com.mygame.angrybirds.Material;

import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {
    @Test
    void materialInitialHealthIsSetCorrectly() {
        Material material = new Material();
        material.health = 100;
        assertEquals(100, material.health);
    }

    @Test
    void materialTakeDamageReducesHealth() {
        Material material = new Material();
        material.health = 100;
        material.takeDamage(10);
        assertEquals(90, material.health);
    }

    @Test
    void materialIsDestroyedWhenHealthIsZero() {
        Material material = new Material();
        material.health = 100;
        material.takeDamage(100);
        assertTrue(material.isDestroyed());
    }

    @Test
    void materialIsNotDestroyedWhenHealthIsAboveZero() {
        Material material = new Material();
        material.health = 100;
        material.takeDamage(50);
        assertFalse(material.isDestroyed());
    }
}
