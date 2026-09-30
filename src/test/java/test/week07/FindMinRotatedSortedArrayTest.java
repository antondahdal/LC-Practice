package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.FindMinRotatedSortedArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindMinRotatedSortedArrayTest {

    private FindMinRotatedSortedArray sol;

    @BeforeEach
    void setUp() {
        sol = new FindMinRotatedSortedArray();
    }

    @Test
    void exampleOne() {
        assertEquals(1, sol.findMin(new int[]{3, 4, 5, 1, 2}));
    }

    @Test
    void exampleTwo() {
        assertEquals(0, sol.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }

    @Test
    void notRotated() {
        assertEquals(11, sol.findMin(new int[]{11, 13, 15, 17}));
    }

    @Test
    void single() {
        assertEquals(7, sol.findMin(new int[]{7}));
    }

    @Test
    void twoRotated() {
        assertEquals(1, sol.findMin(new int[]{2, 1}));
    }

    @Test
    void minAtLastIndex() {
        assertEquals(1, sol.findMin(new int[]{2, 3, 4, 5, 1}));
    }

    @Test
    void negatives() {
        assertEquals(-5, sol.findMin(new int[]{0, 3, -5, -2}));
    }
}
