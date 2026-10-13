package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week09.LetterCombinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LetterCombinationsTest {

    private LetterCombinations sol;

    @BeforeEach
    void setUp() {
        sol = new LetterCombinations();
    }

    @Test
    void example23() {
        List<String> actual = sol.letterCombinations("23");
        assertEquals(9, actual.size());
        assertEquals(
                Set.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"),
                new HashSet<>(actual)
        );
    }

    @Test
    void emptyDigits() {
        assertTrue(sol.letterCombinations("").isEmpty());
    }

    @Test
    void oneKey() {
        List<String> actual = sol.letterCombinations("2");
        assertEquals(3, actual.size());
        assertEquals(Set.of("a", "b", "c"), new HashSet<>(actual));
    }

    @Test
    void fourLetters() {
        List<String> actual = sol.letterCombinations("7");
        assertEquals(4, actual.size());
        assertEquals(Set.of("p", "q", "r", "s"), new HashSet<>(actual));
    }

    @Test
    void sameKeyTwice() {
        List<String> actual = sol.letterCombinations("22");
        assertEquals(9, actual.size());
        assertEquals(
                Set.of("aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc"),
                new HashSet<>(actual)
        );
    }
}
