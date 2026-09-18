package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.SetMatrixZeroes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SetMatrixZeroesTest {

    private SetMatrixZeroes sol;

    @BeforeEach
    void setUp() {
        sol = new SetMatrixZeroes();
    }

    @Test
    void basic() {
        int[][] matrix = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        sol.setZeroes(matrix);
        assertArrayEquals(new int[] {1, 0, 1}, matrix[0]);
        assertArrayEquals(new int[] {0, 0, 0}, matrix[1]);
        assertArrayEquals(new int[] {1, 0, 1}, matrix[2]);
    }

    @Test
    void trapFromNotes() {
        int[][] matrix = {{1, 0, 3}, {4, 5, 6}, {0, 8, 9}};
        sol.setZeroes(matrix);
        assertArrayEquals(new int[] {0, 0, 0}, matrix[0]);
        assertArrayEquals(new int[] {0, 0, 6}, matrix[1]);
        assertArrayEquals(new int[] {0, 0, 0}, matrix[2]);
    }
}
