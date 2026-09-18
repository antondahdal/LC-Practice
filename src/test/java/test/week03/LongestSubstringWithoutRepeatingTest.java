package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.LongestSubstringWithoutRepeating;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestSubstringWithoutRepeatingTest {

    private LongestSubstringWithoutRepeating sol;

    @BeforeEach
    void setUp() {
        sol = new LongestSubstringWithoutRepeating();
    }

    @Test
    void abcabcbb() {
        assertEquals(3, sol.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    void bbbbb() {
        assertEquals(1, sol.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    void pwwkew() {
        assertEquals(3, sol.lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    void dvdf() {
        assertEquals(3, sol.lengthOfLongestSubstring("dvdf"));
    }
}
