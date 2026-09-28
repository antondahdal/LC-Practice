package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.KthLargest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthLargestTest {

    private KthLargest sol;

    @BeforeEach
    void setUp() {
        sol = new KthLargest();
    }

    @Test
    void exampleOne() {
        assertEquals(5, sol.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }

    @Test
    void exampleWithDuplicates() {
        assertEquals(4, sol.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }

    @Test
    void singleElement() {
        assertEquals(1, sol.findKthLargest(new int[]{1}, 1));
    }

    @Test
    void kIsLengthGivesMin() {
        assertEquals(-7, sol.findKthLargest(new int[]{4, -7, 0, 9, 2}, 5));
    }

    @Test
    void allSame() {
        assertEquals(2, sol.findKthLargest(new int[]{2, 2, 2, 2}, 3));
    }

    @Test
    void negatives() {
        assertEquals(-2, sol.findKthLargest(new int[]{-1, -5, -2, -9}, 2));
    }
}
