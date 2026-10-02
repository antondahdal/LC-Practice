package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.Sqrt;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqrtTest {

    private Sqrt sol;

    @BeforeEach
    void setUp() {
        sol = new Sqrt();
    }

    @Test
    void perfectSquare() {
        assertEquals(2, sol.mySqrt(4));
    }

    @Test
    void roundsDown() {
        assertEquals(2, sol.mySqrt(8));
    }

    @Test
    void zero() {
        assertEquals(0, sol.mySqrt(0));
    }

    @Test
    void one() {
        assertEquals(1, sol.mySqrt(1));
    }

    @Test
    void justBelowSquare() {
        assertEquals(4, sol.mySqrt(24));
    }

    @Test
    void largeSquare() {
        assertEquals(46340, sol.mySqrt(2147395600));
    }

    @Test
    void maxInt() {
        assertEquals(46340, sol.mySqrt(Integer.MAX_VALUE));
    }
}
