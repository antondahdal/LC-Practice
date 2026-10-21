package test.week10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week10.JumpGameII;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JumpGameIITest {

    private JumpGameII sol;

    @BeforeEach
    void setUp() {
        sol = new JumpGameII();
    }

    @Test
    void twoJumps() {
        assertEquals(2, sol.jump(new int[]{2, 3, 1, 1, 4}));
    }

    @Test
    void zeroInTheMiddle() {
        assertEquals(2, sol.jump(new int[]{2, 3, 0, 1, 4}));
    }

    @Test
    void oneJump() {
        assertEquals(1, sol.jump(new int[]{1, 2}));
    }

    @Test
    void alreadyThere() {
        assertEquals(0, sol.jump(new int[]{0}));
    }

    @Test
    void stepsOfOne() {
        assertEquals(3, sol.jump(new int[]{1, 1, 1, 1}));
    }
}
