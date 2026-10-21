package test.week10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week10.TwoSum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TwoSumTest {

    private TwoSum sol;

    @BeforeEach
    void setUp() {
        sol = new TwoSum();
    }

    @Test
    void example() {
        assertPair(new int[]{2, 7, 11, 15}, 9, 0, 1);
    }

    @Test
    void pairInTheMiddle() {
        assertPair(new int[]{3, 2, 4}, 6, 1, 2);
    }

    @Test
    void sameValueTwice() {
        assertPair(new int[]{3, 3}, 6, 0, 1);
    }

    @Test
    void negatives() {
        assertPair(new int[]{-1, -2, -3, -4, -5}, -8, 2, 4);
    }

    private void assertPair(int[] nums, int target, int i, int j) {
        int[] got = sol.twoSum(nums, target);
        assertEquals(2, got.length);
        assertTrue((got[0] == i && got[1] == j) || (got[0] == j && got[1] == i));
    }
}
