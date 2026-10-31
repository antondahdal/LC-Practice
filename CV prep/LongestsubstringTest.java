import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestsubstringTest {

    private LongestSubstring sol;

    @BeforeEach
    void setUp() {
        sol = new LongestSubstring();
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
    void empty() {
        assertEquals(0, sol.lengthOfLongestSubstring(""));
    }

    @Test
    void au() {
        assertEquals(2, sol.lengthOfLongestSubstring("au"));
    }

    @Test
    void dvdf() {
        assertEquals(3, sol.lengthOfLongestSubstring("dvdf"));
    }
}
