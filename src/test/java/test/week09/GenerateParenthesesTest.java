package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week09.GenerateParentheses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenerateParenthesesTest {

    private GenerateParentheses sol;

    @BeforeEach
    void setUp() {
        sol = new GenerateParentheses();
    }

    @Test
    void onePair() {
        assertEquals(Set.of("()"), new HashSet<>(sol.generateParenthesis(1)));
    }

    @Test
    void twoPairs() {
        List<String> actual = sol.generateParenthesis(2);
        assertEquals(2, actual.size());
        assertEquals(Set.of("(())", "()()"), new HashSet<>(actual));
    }

    @Test
    void threePairs() {
        List<String> actual = sol.generateParenthesis(3);
        assertEquals(5, actual.size());
        assertEquals(
                Set.of("((()))", "(()())", "(())()", "()(())", "()()()"),
                new HashSet<>(actual)
        );
    }
}
