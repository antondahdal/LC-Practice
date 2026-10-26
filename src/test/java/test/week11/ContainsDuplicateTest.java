package test.week11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week11.ContainsDuplicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainsDuplicateTest {

    private ContainsDuplicate sol;

    @BeforeEach
    void setUp() {
        sol = new ContainsDuplicate();
    }

    @Test
    void valueRepeats() {
        assertTrue(sol.containsDuplicate(new int[]{1, 2, 3, 1}));
    }

    @Test
    void allDistinct() {
        assertFalse(sol.containsDuplicate(new int[]{1, 2, 3, 4}));
    }

    @Test
    void manyRepeats() {
        assertTrue(sol.containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));
    }

    @Test
    void singleValue() {
        assertFalse(sol.containsDuplicate(new int[]{7}));
    }

    @Test
    void negatives() {
        assertTrue(sol.containsDuplicate(new int[]{-1, -2, -1}));
    }
}
