package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.RansomNote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RansomNoteTest {

    private RansomNote sol;

    @BeforeEach
    void setUp() {
        sol = new RansomNote();
    }

    @Test
    void extrasAllowed() {
        assertTrue(sol.canConstruct("aa", "aab"));
    }

    @Test
    void notEnough() {
        assertFalse(sol.canConstruct("aa", "ab"));
    }

    @Test
    void single() {
        assertFalse(sol.canConstruct("a", "b"));
    }
}
