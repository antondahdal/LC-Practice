package test.week08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week08.ClimbingStairs;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClimbingStairsTest {

    private ClimbingStairs sol;

    @BeforeEach
    void setUp() {
        sol = new ClimbingStairs();
    }

    @Test
    void oneStep() {
        assertEquals(1, sol.climbStairs(1));
    }

    @Test
    void twoSteps() {
        assertEquals(2, sol.climbStairs(2));
    }

    @Test
    void threeSteps() {
        assertEquals(3, sol.climbStairs(3));
    }

    @Test
    void fiveSteps() {
        assertEquals(8, sol.climbStairs(5));
    }

    @Test
    void largestInput() {
        assertEquals(1836311903, sol.climbStairs(45));
    }
}
