package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.SearchRotatedSortedArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchRotatedSortedArrayTest {

    private SearchRotatedSortedArray sol;

    @BeforeEach
    void setUp() {
        sol = new SearchRotatedSortedArray();
    }

    @Test
    void exampleOne() {
        assertEquals(4, sol.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }

    @Test
    void exampleTwoMissing() {
        assertEquals(-1, sol.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
    }

    @Test
    void singleMissing() {
        assertEquals(-1, sol.search(new int[]{1}, 0));
    }

    @Test
    void singleFound() {
        assertEquals(0, sol.search(new int[]{1}, 1));
    }

    @Test
    void notRotated() {
        assertEquals(3, sol.search(new int[]{1, 2, 3, 4, 5, 6}, 4));
    }

    @Test
    void targetInLeftPart() {
        assertEquals(1, sol.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 5));
    }

    @Test
    void targetAtEdges() {
        int[] nums = {6, 7, 1, 2, 3, 4, 5};
        assertEquals(0, sol.search(nums, 6));
        assertEquals(6, sol.search(nums, 5));
    }

    @Test
    void twoElementsRotated() {
        assertEquals(1, sol.search(new int[]{3, 1}, 1));
        assertEquals(0, sol.search(new int[]{3, 1}, 3));
    }

    @Test
    void rotatedByOne() {
        assertEquals(0, sol.search(new int[]{5, 1, 2, 3, 4}, 5));
        assertEquals(4, sol.search(new int[]{5, 1, 2, 3, 4}, 4));
    }
}
