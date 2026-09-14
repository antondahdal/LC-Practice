package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.WordPattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordPatternTest {

    private WordPattern sol;

    @BeforeEach
    void setUp() {
        sol = new WordPattern();
    }

    @Test
    void matchingPattern() {
        assertTrue(sol.wordPattern("abba", "dog cat cat dog"));
    }

    @Test
    void lastWordDifferent() {
        assertFalse(sol.wordPattern("abba", "dog cat cat fish"));
    }

    @Test
    void allLettersSameWordsDifferent() {
        assertFalse(sol.wordPattern("aaaa", "dog cat cat dog"));
    }

    @Test
    void lettersDifferentSameWord() {
        assertFalse(sol.wordPattern("abba", "dog dog dog dog"));
    }
}
