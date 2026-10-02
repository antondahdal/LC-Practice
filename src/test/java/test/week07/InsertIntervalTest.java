package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.InsertInterval;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InsertIntervalTest {

    private InsertInterval sol;

    @BeforeEach
    void setUp() {
        sol = new InsertInterval();
    }

    @Test
    void exampleOne() {
        assertArrayEquals(new int[][]{{1, 5}, {6, 9}},
                sol.insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}));
    }

    @Test
    void exampleTwo() {
        assertArrayEquals(new int[][]{{1, 2}, {3, 10}, {12, 16}},
                sol.insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}));
    }

    @Test
    void emptyList() {
        assertArrayEquals(new int[][]{{5, 7}},
                sol.insert(new int[][]{}, new int[]{5, 7}));
    }

    @Test
    void goesFirst() {
        assertArrayEquals(new int[][]{{0, 1}, {3, 5}, {8, 9}},
                sol.insert(new int[][]{{3, 5}, {8, 9}}, new int[]{0, 1}));
    }

    @Test
    void goesLast() {
        assertArrayEquals(new int[][]{{1, 2}, {3, 5}, {10, 12}},
                sol.insert(new int[][]{{1, 2}, {3, 5}}, new int[]{10, 12}));
    }

    @Test
    void goesBetweenNoMerge() {
        assertArrayEquals(new int[][]{{1, 2}, {4, 5}, {7, 8}},
                sol.insert(new int[][]{{1, 2}, {7, 8}}, new int[]{4, 5}));
    }

    @Test
    void swallowsAll() {
        assertArrayEquals(new int[][]{{0, 20}},
                sol.insert(new int[][]{{1, 2}, {4, 5}, {7, 8}}, new int[]{0, 20}));
    }

    @Test
    void touchingEndsMerge() {
        assertArrayEquals(new int[][]{{1, 7}},
                sol.insert(new int[][]{{1, 3}, {5, 7}}, new int[]{3, 5}));
    }

    @Test
    void insideOne() {
        assertArrayEquals(new int[][]{{1, 10}},
                sol.insert(new int[][]{{1, 10}}, new int[]{3, 4}));
    }
}
