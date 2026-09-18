package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.MaximumSubarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumSubarrayTest {

    private MaximumSubarray sol;

    @BeforeEach
    void setUp() {
        sol = new MaximumSubarray();
    }

    @Test
    void classic() {
        assertEquals(6, sol.maxSubArray(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void single() {
        assertEquals(1, sol.maxSubArray(new int[] {1}));
    }

    @Test
    void allNegative() {
        assertEquals(-1, sol.maxSubArray(new int[] {-2, -1}));
    }
}
