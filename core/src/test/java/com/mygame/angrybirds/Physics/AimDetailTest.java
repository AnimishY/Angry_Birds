package com.mygame.angrybirds.Physics;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AimDetailTest {

    @Test
    void initialPositionIsSetCorrectly() {
        AimDetail aimDetail = new AimDetail(10, 20);
        aimDetail.setInitialPosition(5, 5);
        Vector2 position = aimDetail.getPosition(0);
        assertEquals(5, position.x, 0.01);
        assertEquals(5, position.y, 0.01);
    }

    @Test
    void positionUpdatesCorrectlyWithTime() {
        AimDetail aimDetail = new AimDetail(10, 20);
        aimDetail.setInitialPosition(0, 0);
        aimDetail.update(1);
        Vector2 position = aimDetail.getPosition(1);
        assertEquals(10, position.x, 0.01);
        assertEquals(-5, position.y, 0.01);
    }

    @Test
    void velocityUpdatesCorrectlyWithTime() {
        AimDetail aimDetail = new AimDetail(10, 20);
        aimDetail.update(1);
        Vector2 velocity = aimDetail.getVelocity();
        assertEquals(10, velocity.x, 0.01);
        assertEquals(-30, velocity.y, 0.01);
    }

    @Test
    void velocityResetsTimeWhenChanged() {
        AimDetail aimDetail = new AimDetail(10, 20);
        aimDetail.update(1);
        aimDetail.setVelocity(15, 25);
        Vector2 velocity = aimDetail.getVelocity();
        assertEquals(15, velocity.x, 0.01);
        assertEquals(25, velocity.y, 0.01);
    }

    @Test
    void positionWithNegativeInitialVelocity() {
        AimDetail aimDetail = new AimDetail(-10, -20);
        aimDetail.setInitialPosition(0, 0);
        aimDetail.update(1);
        Vector2 position = aimDetail.getPosition(1);
        assertEquals(-10, position.x, 0.01);
        assertEquals(-45, position.y, 0.01);
    }
}
