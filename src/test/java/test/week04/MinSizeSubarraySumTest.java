package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.MinSizeSubarraySum;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinSizeSubarraySumTest {

    private MinSizeSubarraySum sol;

    @BeforeEach
    void setUp() {
        sol = new MinSizeSubarraySum();
    }

    @Test
    void classic() {
        assertEquals(2, sol.minSubArrayLen(7, new int[] {2, 3, 1, 2, 4, 3}));
    }

    @Test
    void noStretch() {
        assertEquals(0, sol.minSubArrayLen(11, new int[] {1, 1, 1, 1}));
    }

    @Test
    void wholeArray() {
        assertEquals(1, sol.minSubArrayLen(4, new int[] {1, 4, 4}));
    }
}
