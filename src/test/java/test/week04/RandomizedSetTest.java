package test.week04;

import org.junit.jupiter.api.Test;
import questions.week04.RandomizedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomizedSetTest {

    @Test
    void insertRemoveGetRandom() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(1));
        assertFalse(set.remove(2));
        assertTrue(set.insert(2));
        int r = set.getRandom();
        assertTrue(r == 1 || r == 2);
        assertTrue(set.remove(1));
        assertFalse(set.insert(2));
        assertEquals(2, set.getRandom());
    }

    @Test
    void duplicateInsertFalse() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(2));
        assertFalse(set.insert(2));
        assertTrue(set.remove(2));
        assertFalse(set.remove(2));
    }
}
