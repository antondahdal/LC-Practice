package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.CharacterReplacement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterReplacementTest {

    private CharacterReplacement sol;

    @BeforeEach
    void setUp() {
        sol = new CharacterReplacement();
    }

    @Test
    void abab() {
        assertEquals(4, sol.characterReplacement("ABAB", 2));
    }

    @Test
    void aababba() {
        assertEquals(4, sol.characterReplacement("AABABBA", 1));
    }

    @Test
    void baaab() {
        assertEquals(5, sol.characterReplacement("BAAAB", 2));
    }
}
