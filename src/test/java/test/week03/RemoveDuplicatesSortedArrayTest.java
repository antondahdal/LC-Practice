package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.RemoveDuplicatesSortedArray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesSortedArrayTest {

    private RemoveDuplicatesSortedArray sol;

    @BeforeEach
    void setUp() {
        sol = new RemoveDuplicatesSortedArray();
    }

    @Test
    void basic() {
        int[] nums = {1, 1, 2};
        int k = sol.removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] {1, 2}, java.util.Arrays.copyOf(nums, k));
    }

    @Test
    void manyDupes() {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k = sol.removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] {0, 1, 2, 3, 4}, java.util.Arrays.copyOf(nums, k));
    }
}
