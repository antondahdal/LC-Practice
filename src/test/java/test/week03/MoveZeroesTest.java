package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.MoveZeroes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MoveZeroesTest {

    private MoveZeroes sol;

    @BeforeEach
    void setUp() {
        sol = new MoveZeroes();
    }

    @Test
    void basic() {
        int[] nums = {0, 1, 0, 3, 12};
        sol.moveZeroes(nums);
        assertArrayEquals(new int[] {1, 3, 12, 0, 0}, nums);
    }

    @Test
    void alreadyMoved() {
        int[] nums = {1, 2, 3};
        sol.moveZeroes(nums);
        assertArrayEquals(new int[] {1, 2, 3}, nums);
    }
}
