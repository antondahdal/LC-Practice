package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.MergeSortedArray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortedArrayTest {

    private MergeSortedArray sol;

    @BeforeEach
    void setUp() {
        sol = new MergeSortedArray();
    }

    @Test
    void basic() {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        sol.merge(nums1, 3, new int[] {2, 5, 6}, 3);
        assertArrayEquals(new int[] {1, 2, 2, 3, 5, 6}, nums1);
    }

    @Test
    void nums2Empty() {
        int[] nums1 = {1};
        sol.merge(nums1, 1, new int[] {}, 0);
        assertArrayEquals(new int[] {1}, nums1);
    }

    @Test
    void nums1Empty() {
        int[] nums1 = {0};
        sol.merge(nums1, 0, new int[] {1}, 1);
        assertArrayEquals(new int[] {1}, nums1);
    }
}
