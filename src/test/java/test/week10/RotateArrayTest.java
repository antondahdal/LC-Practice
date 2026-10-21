package test.week10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week10.RotateArray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RotateArrayTest {

    private RotateArray sol;

    @BeforeEach
    void setUp() {
        sol = new RotateArray();
    }

    @Test
    void rotateByThree() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums, 3);
        assertArrayEquals(new int[]{5, 6, 7, 1, 2, 3, 4}, nums);
    }

    @Test
    void rotateByTwo() {
        int[] nums = {-1, -100, 3, 99};
        sol.rotate(nums, 2);
        assertArrayEquals(new int[]{3, 99, -1, -100}, nums);
    }

    @Test
    void kLargerThanLength() {
        int[] nums = {1, 2};
        sol.rotate(nums, 3);
        assertArrayEquals(new int[]{2, 1}, nums);
    }

    @Test
    void kZero() {
        int[] nums = {1, 2, 3};
        sol.rotate(nums, 0);
        assertArrayEquals(new int[]{1, 2, 3}, nums);
    }

    @Test
    void singleElement() {
        int[] nums = {1};
        sol.rotate(nums, 4);
        assertArrayEquals(new int[]{1}, nums);
    }
}
