package test.week08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week08.HouseRobber;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseRobberTest {

    private HouseRobber sol;

    @BeforeEach
    void setUp() {
        sol = new HouseRobber();
    }

    @Test
    void example1() {
        assertEquals(4, sol.rob(new int[]{1, 2, 3, 1}));
    }

    @Test
    void example2() {
        assertEquals(12, sol.rob(new int[]{2, 7, 9, 3, 1}));
    }

    @Test
    void singleHouse() {
        assertEquals(5, sol.rob(new int[]{5}));
    }

    @Test
    void twoHouses() {
        assertEquals(3, sol.rob(new int[]{2, 3}));
    }

    @Test
    void skipTwoInARow() {
        assertEquals(4, sol.rob(new int[]{2, 1, 1, 2}));
    }

    @Test
    void allZero() {
        assertEquals(0, sol.rob(new int[]{0, 0, 0}));
    }
}
