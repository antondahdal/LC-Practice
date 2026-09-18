package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.PalindromeLinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PalindromeLinkedListTest {

    private PalindromeLinkedList sol;

    @BeforeEach
    void setUp() {
        sol = new PalindromeLinkedList();
    }

    @Test
    void evenPalindrome() {
        assertTrue(sol.isPalindrome(list(1, 2, 2, 1)));
    }

    @Test
    void oddPalindrome() {
        assertTrue(sol.isPalindrome(list(1, 2, 1)));
    }

    @Test
    void notPalindrome() {
        assertFalse(sol.isPalindrome(list(1, 2)));
    }

    @Test
    void singleNode() {
        assertTrue(sol.isPalindrome(list(1)));
    }

    @Test
    void emptyList() {
        assertTrue(sol.isPalindrome(null));
    }

    private static ListNode list(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }
}
