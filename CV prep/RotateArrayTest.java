import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RotateArrayTest {

    private RotateArray sol;

    @BeforeEach
    void setUp() {
        sol = new RotateArray();
    }

    @Test
    void exampleK3() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums, 3);
        assertArrayEquals(new int[]{5, 6, 7, 1, 2, 3, 4}, nums);
    }

    @Test
    void kZero() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, nums);
    }

    @Test
    void kEqualToLength() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums, 7);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, nums);
    }

    @Test
    void kGreaterThanLength() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        sol.rotate(nums, 10);
        assertArrayEquals(new int[]{5, 6, 7, 1, 2, 3, 4}, nums);
    }

    @Test
    void kTwiceLength() {
        int[] nums = {1, 2, 3};
        sol.rotate(nums, 6);
        assertArrayEquals(new int[]{1, 2, 3}, nums);
    }

    @Test
    void emptyKZero() {
        int[] nums = {};
        sol.rotate(nums, 0);
        assertArrayEquals(new int[]{}, nums);
    }

    @Test
    void emptyK3() {
        int[] nums = {};
        sol.rotate(nums, 3);
        assertArrayEquals(new int[]{}, nums);
    }

    @Test
    void singleKZero() {
        int[] nums = {1};
        sol.rotate(nums, 0);
        assertArrayEquals(new int[]{1}, nums);
    }

    @Test
    void singleK1() {
        int[] nums = {1};
        sol.rotate(nums, 1);
        assertArrayEquals(new int[]{1}, nums);
    }

    @Test
    void singleK5() {
        int[] nums = {1};
        sol.rotate(nums, 5);
        assertArrayEquals(new int[]{1}, nums);
    }

    @Test
    void negativeValuesK2() {
        int[] nums = {-1, -100, 3, 99};
        sol.rotate(nums, 2);
        assertArrayEquals(new int[]{3, 99, -1, -100}, nums);
    }
}
