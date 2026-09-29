package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.MergeIntervals;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeIntervalsTest {

    private MergeIntervals sol;

    @BeforeEach
    void setUp() {
        sol = new MergeIntervals();
    }

    @Test
    void exampleOne() {
        assertArrayEquals(new int[][]{{1, 6}, {8, 10}, {15, 18}},
                sol.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}));
    }

    @Test
    void touchingCountsAsOverlap() {
        assertArrayEquals(new int[][]{{1, 5}},
                sol.merge(new int[][]{{1, 4}, {4, 5}}));
    }

    @Test
    void singleInterval() {
        assertArrayEquals(new int[][]{{1, 4}},
                sol.merge(new int[][]{{1, 4}}));
    }

    @Test
    void unsortedInput() {
        assertArrayEquals(new int[][]{{0, 4}},
                sol.merge(new int[][]{{1, 4}, {0, 4}}));
    }

    @Test
    void innerIntervalDoesNotShrinkEnd() {
        assertArrayEquals(new int[][]{{1, 10}},
                sol.merge(new int[][]{{1, 10}, {2, 3}}));
    }

    @Test
    void noOverlapStaysApart() {
        assertArrayEquals(new int[][]{{1, 2}, {3, 4}, {5, 6}},
                sol.merge(new int[][]{{5, 6}, {1, 2}, {3, 4}}));
    }

    @Test
    void chainMergesIntoOne() {
        assertArrayEquals(new int[][]{{1, 10}},
                sol.merge(new int[][]{{2, 5}, {1, 3}, {4, 8}, {7, 10}}));
    }
}
