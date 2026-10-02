package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.MinArrowsBurstBalloons;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinArrowsBurstBalloonsTest {

    private MinArrowsBurstBalloons sol;

    @BeforeEach
    void setUp() {
        sol = new MinArrowsBurstBalloons();
    }

    @Test
    void exampleOne() {
        assertEquals(2, sol.findMinArrowShots(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}}));
    }

    @Test
    void noOverlap() {
        assertEquals(4, sol.findMinArrowShots(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}));
    }

    @Test
    void touchingEnds() {
        assertEquals(2, sol.findMinArrowShots(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}}));
    }

    @Test
    void single() {
        assertEquals(1, sol.findMinArrowShots(new int[][]{{5, 9}}));
    }

    @Test
    void nested() {
        assertEquals(2, sol.findMinArrowShots(new int[][]{{1, 10}, {2, 3}, {4, 5}}));
    }

    @Test
    void allShareOnePoint() {
        assertEquals(1, sol.findMinArrowShots(new int[][]{{1, 5}, {2, 6}, {3, 7}, {4, 8}}));
    }

    @Test
    void extremeValues() {
        assertEquals(2, sol.findMinArrowShots(new int[][]{
                {-2147483646, -2147483645}, {2147483646, 2147483647}}));
    }
}
