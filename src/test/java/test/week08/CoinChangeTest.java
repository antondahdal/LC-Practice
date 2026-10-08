package test.week08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week08.CoinChange;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoinChangeTest {

    private CoinChange sol;

    @BeforeEach
    void setUp() {
        sol = new CoinChange();
    }

    @Test
    void example1() {
        assertEquals(3, sol.coinChange(new int[]{1, 2, 5}, 11));
    }

    @Test
    void impossible() {
        assertEquals(-1, sol.coinChange(new int[]{2}, 3));
    }

    @Test
    void amountZero() {
        assertEquals(0, sol.coinChange(new int[]{1}, 0));
    }

    @Test
    void onlyOnes() {
        assertEquals(2, sol.coinChange(new int[]{1}, 2));
    }

    @Test
    void twoOfTheMiddle() {
        assertEquals(2, sol.coinChange(new int[]{1, 3, 4}, 6));
    }
}
