package test.week07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week07.SearchInsertPosition;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInsertPositionTest {

    private SearchInsertPosition sol;

    @BeforeEach
    void setUp() {
        sol = new SearchInsertPosition();
    }

    @Test
    void found() {
        assertEquals(2, sol.searchInsert(new int[]{1, 3, 5, 6}, 5));
    }

    @Test
    void insertInMiddle() {
        assertEquals(1, sol.searchInsert(new int[]{1, 3, 5, 6}, 2));
    }

    @Test
    void insertAtEnd() {
        assertEquals(4, sol.searchInsert(new int[]{1, 3, 5, 6}, 7));
    }

    @Test
    void insertAtStart() {
        assertEquals(0, sol.searchInsert(new int[]{1, 3, 5, 6}, 0));
    }

    @Test
    void singleElement() {
        assertEquals(0, sol.searchInsert(new int[]{1}, 1));
        assertEquals(1, sol.searchInsert(new int[]{1}, 2));
    }

    @Test
    void negatives() {
        assertEquals(2, sol.searchInsert(new int[]{-9, -4, 0, 3, 8}, -1));
    }

    @Test
    void negativeBetweenTwo() {
        assertEquals(1, sol.searchInsert(new int[]{-5, -3}, -4));
    }
}
