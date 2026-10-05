package test.week08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week08.NumberOfIslands;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfIslandsTest {

    private NumberOfIslands sol;

    @BeforeEach
    void setUp() {
        sol = new NumberOfIslands();
    }

    @Test
    void oneIsland() {
        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        assertEquals(1, sol.numIslands(grid));
    }

    @Test
    void threeIslands() {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        assertEquals(3, sol.numIslands(grid));
    }

    @Test
    void allWater() {
        char[][] grid = {
                {'0', '0'},
                {'0', '0'}
        };
        assertEquals(0, sol.numIslands(grid));
    }

    @Test
    void singleLand() {
        assertEquals(1, sol.numIslands(new char[][]{{'1'}}));
    }

    @Test
    void cornersDoNotConnect() {
        char[][] grid = {
                {'1', '0'},
                {'0', '1'}
        };
        assertEquals(2, sol.numIslands(grid));
    }
}
