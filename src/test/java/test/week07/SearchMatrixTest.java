package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.SearchMatrix;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchMatrixTest {

    private SearchMatrix sol;

    private final int[][] grid = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
    };

    @BeforeEach
    void setUp() {
        sol = new SearchMatrix();
    }

    @Test
    void exampleFound() {
        assertTrue(sol.searchMatrix(grid, 3));
    }

    @Test
    void exampleMissing() {
        assertFalse(sol.searchMatrix(grid, 13));
    }

    @Test
    void firstCell() {
        assertTrue(sol.searchMatrix(grid, 1));
    }

    @Test
    void lastCell() {
        assertTrue(sol.searchMatrix(grid, 60));
    }

    @Test
    void firstOfMiddleRow() {
        assertTrue(sol.searchMatrix(grid, 10));
    }

    @Test
    void belowAll() {
        assertFalse(sol.searchMatrix(grid, 0));
    }

    @Test
    void aboveAll() {
        assertFalse(sol.searchMatrix(grid, 61));
    }

    @Test
    void singleCell() {
        assertTrue(sol.searchMatrix(new int[][]{{5}}, 5));
        assertFalse(sol.searchMatrix(new int[][]{{5}}, 4));
    }

    @Test
    void singleColumn() {
        assertTrue(sol.searchMatrix(new int[][]{{1}, {3}, {5}}, 3));
        assertFalse(sol.searchMatrix(new int[][]{{1}, {3}, {5}}, 4));
    }
}
