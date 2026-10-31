package test.week11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week11.BestTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeTest {

    private BestTime sol;

    @BeforeEach
    void setUp() {
        sol = new BestTime();
    }

    @Test
    void profitIsFive() {
        assertEquals(5, sol.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    void pricesOnlyFall() {
        assertEquals(0, sol.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    @Test
    void oneDay() {
        assertEquals(0, sol.maxProfit(new int[]{4}));
    }

    @Test
    void buyEarlySellLate() {
        assertEquals(2, sol.maxProfit(new int[]{2, 4, 1}));
    }

    @Test
    void peakThenHigherPeak() {
        assertEquals(6, sol.maxProfit(new int[]{3, 2, 6, 1, 7}));
    }
}
