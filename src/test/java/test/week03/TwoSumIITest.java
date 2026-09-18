package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.TwoSumII;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TwoSumIITest {

    private TwoSumII sol;

    @BeforeEach
    void setUp() {
        sol = new TwoSumII();
    }

    @Test
    void basicPair() {
        assertArrayEquals(new int[] {1, 2}, sol.twoSum(new int[] {2, 7, 11, 15}, 9));
    }

    @Test
    void pairInMiddle() {
        assertArrayEquals(new int[] {1, 3}, sol.twoSum(new int[] {2, 3, 4}, 6));
    }

    @Test
    void negatives() {
        assertArrayEquals(new int[] {1, 2}, sol.twoSum(new int[] {-1, 0}, -1));
    }
}
