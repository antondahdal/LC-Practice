package test.week07;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.TopKFrequent;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TopKFrequentTest {

    private TopKFrequent sol;

    @BeforeEach
    void setUp() {
        sol = new TopKFrequent();
    }

    private int[] sorted(int[] a) {
        int[] copy = a.clone();
        Arrays.sort(copy);
        return copy;
    }

    @Test
    void exampleOne() {
        assertArrayEquals(new int[]{1, 2}, sorted(sol.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }

    @Test
    void singleElement() {
        assertArrayEquals(new int[]{1}, sorted(sol.topKFrequent(new int[]{1}, 1)));
    }

    @Test
    void kEqualsDistinctCount() {
        assertArrayEquals(new int[]{1, 2, 3}, sorted(sol.topKFrequent(new int[]{3, 1, 2, 1, 3, 2}, 3)));
    }

    @Test
    void negativesAndTopOne() {
        assertArrayEquals(new int[]{-1}, sorted(sol.topKFrequent(new int[]{4, -1, -1, 2, -1, 4}, 1)));
    }

    @Test
    void unsortedInput() {
        assertArrayEquals(new int[]{5, 7}, sorted(sol.topKFrequent(new int[]{7, 5, 9, 5, 7, 8, 7, 5, 10}, 2)));
    }
}
