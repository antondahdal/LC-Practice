package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.IsomorphicStrings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsomorphicStringsTest {

    private IsomorphicStrings sol;

    @BeforeEach
    void setUp() {
        sol = new IsomorphicStrings();
    }

    @Test
    void eggAdd() {
        assertTrue(sol.isIsomorphic("egg", "add"));
    }

    @Test
    void fooBar() {
        assertFalse(sol.isIsomorphic("foo", "bar"));
    }

    @Test
    void paperTitle() {
        assertTrue(sol.isIsomorphic("paper", "title"));
    }

    @Test
    void sameCountsStillFalse() {
        assertFalse(sol.isIsomorphic("abab", "aabb"));
    }

    @Test
    void twoLettersOnePartner() {
        assertFalse(sol.isIsomorphic("ab", "aa"));
    }
}
