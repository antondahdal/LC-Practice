package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.SummaryRanges;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryRangesTest {

    private SummaryRanges sol;

    @BeforeEach
    void setUp() {
        sol = new SummaryRanges();
    }

    @Test
    void exampleOne() {
        assertEquals(List.of("0->2", "4->5", "7"),
                sol.summaryRanges(new int[]{0, 1, 2, 4, 5, 7}));
    }

    @Test
    void exampleTwo() {
        assertEquals(List.of("0", "2->4", "6", "8->9"),
                sol.summaryRanges(new int[]{0, 2, 3, 4, 6, 8, 9}));
    }

    @Test
    void empty() {
        assertEquals(List.of(), sol.summaryRanges(new int[]{}));
    }

    @Test
    void singleNumber() {
        assertEquals(List.of("5"), sol.summaryRanges(new int[]{5}));
    }

    @Test
    void oneLongRun() {
        assertEquals(List.of("-3->2"), sol.summaryRanges(new int[]{-3, -2, -1, 0, 1, 2}));
    }

    @Test
    void noRuns() {
        assertEquals(List.of("1", "3", "5"), sol.summaryRanges(new int[]{1, 3, 5}));
    }

    @Test
    void intLimits() {
        assertEquals(List.of("-2147483648->-2147483647", "2147483647"),
                sol.summaryRanges(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MAX_VALUE}));
    }
}
