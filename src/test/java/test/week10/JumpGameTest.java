package test.week10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week10.JumpGame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JumpGameTest {

    private JumpGame sol;

    @BeforeEach
    void setUp() {
        sol = new JumpGame();
    }

    @Test
    void reachesEnd() {
        assertTrue(sol.canJump(new int[]{2, 3, 1, 1, 4}));
    }

    @Test
    void blockedByZero() {
        assertFalse(sol.canJump(new int[]{3, 2, 1, 0, 4}));
    }

    @Test
    void alreadyThere() {
        assertTrue(sol.canJump(new int[]{0}));
    }

    @Test
    void zeroThenMore() {
        assertFalse(sol.canJump(new int[]{0, 1}));
    }

    @Test
    void jumpOverZeros() {
        assertTrue(sol.canJump(new int[]{2, 0, 0}));
    }

    @Test
    void landsOnZero() {
        assertFalse(sol.canJump(new int[]{1, 0, 1}));
    }
}
