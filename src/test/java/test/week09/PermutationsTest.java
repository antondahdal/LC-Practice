package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week09.Permutations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationsTest {

    private Permutations sol;

    @BeforeEach
    void setUp() {
        sol = new Permutations();
    }

    @Test
    void oneNumber() {
        List<List<Integer>> actual = sol.permute(new int[]{1});
        assertEquals(1, actual.size());
        assertEquals(Set.of(List.of(1)), new HashSet<>(actual));
    }

    @Test
    void twoNumbers() {
        List<List<Integer>> actual = sol.permute(new int[]{0, 1});
        assertEquals(2, actual.size());
        assertEquals(Set.of(List.of(0, 1), List.of(1, 0)), new HashSet<>(actual));
    }

    @Test
    void threeNumbers() {
        List<List<Integer>> actual = sol.permute(new int[]{1, 2, 3});
        assertEquals(6, actual.size());
        assertEquals(
                Set.of(
                        List.of(1, 2, 3),
                        List.of(1, 3, 2),
                        List.of(2, 1, 3),
                        List.of(2, 3, 1),
                        List.of(3, 1, 2),
                        List.of(3, 2, 1)
                ),
                new HashSet<>(actual)
        );
    }
}
