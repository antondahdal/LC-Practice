package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.ThreeSum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreeSumTest {

    private ThreeSum sol;

    @BeforeEach
    void setUp() {
        sol = new ThreeSum();
    }

    @Test
    void classic() {
        List<List<Integer>> out = sol.threeSum(new int[] {-1, 0, 1, 2, -1, -4});
        Set<List<Integer>> set = new HashSet<>(out);
        assertEquals(2, set.size());
        assertTrue(set.contains(List.of(-1, -1, 2)));
        assertTrue(set.contains(List.of(-1, 0, 1)));
    }

    @Test
    void allZero() {
        assertEquals(List.of(List.of(0, 0, 0)), sol.threeSum(new int[] {0, 0, 0}));
    }

    @Test
    void notJustNeighbors() {
        List<List<Integer>> out = sol.threeSum(new int[] {-4, 0, 1, 2, 3});
        assertTrue(out.contains(List.of(-4, 1, 3)));
    }
}
