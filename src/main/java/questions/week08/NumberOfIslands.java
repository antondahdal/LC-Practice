package questions.week08;

/**
 * 200. Number of Islands
 * https://leetcode.com/problems/number-of-islands/
 *
 * You get a grid of '1' (land) and '0' (water).
 * An island is land connected up, down, left, or right.
 * A corner touch is not a connection.
 * Return how many islands are in the grid.
 *
 * Example:
 * 1 1 0 0 0
 * 1 1 0 0 0
 * 0 0 1 0 0
 * 0 0 0 1 1
 * -> 3
 */
public class NumberOfIslands {

    public int numIslands(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '1') {
                    count++;
                    sink(grid, row, col);
                }
            }
        }
        return count;
    }

    private void sink(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return;
        if (grid[row][col] == '0') return;

        grid[row][col] = '0';

        sink(grid, row - 1, col);
        sink(grid, row + 1, col);
        sink(grid, row, col - 1);
        sink(grid, row, col + 1);
    }
}
