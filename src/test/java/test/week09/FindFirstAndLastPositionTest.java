package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week09.FindFirstAndLastPosition;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FindFirstAndLastPositionTest {

    private FindFirstAndLastPosition sol;

    @BeforeEach
    void setUp() {
        sol = new FindFirstAndLastPosition();
    }

    @Test
    void exampleFound() {
        assertArrayEquals(new int[]{3, 4}, sol.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
    }

    @Test
    void exampleMissing() {
        assertArrayEquals(new int[]{-1, -1}, sol.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6));
    }

    @Test
    void empty() {
        assertArrayEquals(new int[]{-1, -1}, sol.searchRange(new int[]{}, 0));
    }

    @Test
    void singleFound() {
        assertArrayEquals(new int[]{0, 0}, sol.searchRange(new int[]{1}, 1));
    }

    @Test
    void singleMissing() {
        assertArrayEquals(new int[]{-1, -1}, sol.searchRange(new int[]{1}, 0));
    }

    @Test
    void twoSevens() {
        assertArrayEquals(new int[]{1, 2}, sol.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 7));
    }

    @Test
    void atEnds() {
        assertArrayEquals(new int[]{0, 0}, sol.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 5));
        assertArrayEquals(new int[]{5, 5}, sol.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 10));
    }

    @Test
    void allSame() {
        assertArrayEquals(new int[]{0, 3}, sol.searchRange(new int[]{8, 8, 8, 8}, 8));
    }
}
