package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.LongestConsecutiveSequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestConsecutiveSequenceTest {

    private LongestConsecutiveSequence sol;

    @BeforeEach
    void setUp() {
        sol = new LongestConsecutiveSequence();
    }

    @Test
    void oneRun() {
        assertEquals(4, sol.longestConsecutive(new int[] {100, 4, 200, 1, 3, 2}));
    }

    @Test
    void twoSeparateRuns() {
        assertEquals(2, sol.longestConsecutive(new int[] {1, 2, 10, 11}));
    }

    @Test
    void withNegatives() {
        assertEquals(3, sol.longestConsecutive(new int[] {-1, 0, 1}));
    }

    @Test
    void empty() {
        assertEquals(0, sol.longestConsecutive(new int[] {}));
    }
}
