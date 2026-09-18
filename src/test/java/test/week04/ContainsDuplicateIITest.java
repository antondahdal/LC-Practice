package test.week04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week04.ContainsDuplicateII;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainsDuplicateIITest {

    private ContainsDuplicateII sol;

    @BeforeEach
    void setUp() {
        sol = new ContainsDuplicateII();
    }

    @Test
    void withinK() {
        assertTrue(sol.containsNearbyDuplicate(new int[] {1, 2, 3, 1}, 3));
    }

    @Test
    void tooFar() {
        assertFalse(sol.containsNearbyDuplicate(new int[] {1, 2, 3, 1, 2, 3}, 2));
    }

    @Test
    void adjacentOnes() {
        assertTrue(sol.containsNearbyDuplicate(new int[] {1, 1, 3, 4, 5, 1}, 1));
    }
}
