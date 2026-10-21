package test.week10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week10.ValidParentheses;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidParenthesesTest {

    private ValidParentheses sol;

    @BeforeEach
    void setUp() {
        sol = new ValidParentheses();
    }

    @Test
    void onePair() {
        assertTrue(sol.isValid("()"));
    }

    @Test
    void threeKindsInARow() {
        assertTrue(sol.isValid("()[]{}"));
    }

    @Test
    void nested() {
        assertTrue(sol.isValid("{[]}"));
    }

    @Test
    void mismatchedCloser() {
        assertFalse(sol.isValid("(]"));
    }

    @Test
    void crossed() {
        assertFalse(sol.isValid("([)]"));
    }

    @Test
    void openerLeftOpen() {
        assertFalse(sol.isValid("(("));
    }

    @Test
    void closerWithNothingOpen() {
        assertFalse(sol.isValid(")"));
    }

    @Test
    void closerThenOpener() {
        assertFalse(sol.isValid(")("));
    }

    @Test
    void closerAfterPair() {
        assertFalse(sol.isValid("())"));
    }

    @Test
    void closerAfterTwoPairs() {
        assertFalse(sol.isValid("()())"));
    }
}
