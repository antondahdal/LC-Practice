package test.week11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week11.RemoveDuplicatesII;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesIITest {

    private RemoveDuplicatesII sol;

    @BeforeEach
    void setUp() {
        sol = new RemoveDuplicatesII();
    }

    @Test
    void thirdCopyDropped() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = sol.removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[]{1, 1, 2, 2, 3}, Arrays.copyOf(nums, k));
    }

    @Test
    void twoCopiesKept() {
        int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int k = sol.removeDuplicates(nums);
        assertEquals(7, k);
        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 3, 3}, Arrays.copyOf(nums, k));
    }

    @Test
    void allSame() {
        int[] nums = {1, 1, 1, 1};
        int k = sol.removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[]{1, 1}, Arrays.copyOf(nums, k));
    }

    @Test
    void noDuplicates() {
        int[] nums = {1, 2, 3};
        int k = sol.removeDuplicates(nums);
        assertEquals(3, k);
        assertArrayEquals(new int[]{1, 2, 3}, Arrays.copyOf(nums, k));
    }

    @Test
    void oneValue() {
        int[] nums = {4};
        int k = sol.removeDuplicates(nums);
        assertEquals(1, k);
        assertArrayEquals(new int[]{4}, Arrays.copyOf(nums, k));
    }
}
