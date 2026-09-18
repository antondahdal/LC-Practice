package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.ContainerWithMostWater;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerWithMostWaterTest {

    private ContainerWithMostWater sol;

    @BeforeEach
    void setUp() {
        sol = new ContainerWithMostWater();
    }

    @Test
    void classic() {
        assertEquals(49, sol.maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    @Test
    void twoWalls() {
        assertEquals(1, sol.maxArea(new int[] {1, 1}));
    }
}
