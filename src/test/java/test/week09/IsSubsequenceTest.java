package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week09.IsSubsequence;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsSubsequenceTest {

    private IsSubsequence sol;

    @BeforeEach
    void setUp() {
        sol = new IsSubsequence();
    }

    @Test
    void exampleTrue() {
        assertTrue(sol.isSubsequence("abc", "ahbgdc"));
    }

    @Test
    void exampleFalse() {
        assertFalse(sol.isSubsequence("axc", "ahbgdc"));
    }

    @Test
    void emptyS() {
        assertTrue(sol.isSubsequence("", "ahbgdc"));
    }

    @Test
    void emptyT() {
        assertFalse(sol.isSubsequence("a", ""));
    }

    @Test
    void sameString() {
        assertTrue(sol.isSubsequence("abc", "abc"));
    }

    @Test
    void singleLetterMissing() {
        assertFalse(sol.isSubsequence("a", "b"));
    }

    @Test
    void lastLetterMissing() {
        assertFalse(sol.isSubsequence("abc", "ab"));
    }
}
