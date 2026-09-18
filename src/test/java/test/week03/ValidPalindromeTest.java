package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.ValidPalindrome;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidPalindromeTest {

    private ValidPalindrome sol;

    @BeforeEach
    void setUp() {
        sol = new ValidPalindrome();
    }

    @Test
    void panama() {
        assertTrue(sol.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    void raceACar() {
        assertFalse(sol.isPalindrome("race a car"));
    }

    @Test
    void onlySpace() {
        assertTrue(sol.isPalindrome(" "));
    }

    @Test
    void zeroAndP() {
        assertFalse(sol.isPalindrome("0P"));
    }
}
