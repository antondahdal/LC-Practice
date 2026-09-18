package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.RemoveDuplicatesSortedListII;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RemoveDuplicatesSortedListIITest {

    private RemoveDuplicatesSortedListII sol;

    @BeforeEach
    void setUp() {
        sol = new RemoveDuplicatesSortedListII();
    }

    @Test
    void dropsRunsInTheMiddleAndKeepsUniques() {
        assertArrayEquals(
                new int[] {1, 2, 5},
                toArray(sol.deleteDuplicates(list(1, 2, 3, 3, 4, 4, 5))));
    }

    @Test
    void dropsARunAtTheHead() {
        assertArrayEquals(
                new int[] {2, 3},
                toArray(sol.deleteDuplicates(list(1, 1, 1, 2, 3))));
    }

    @Test
    void allDuplicatesBecomeEmpty() {
        assertNull(sol.deleteDuplicates(list(1, 1)));
    }

    @Test
    void noDuplicatesStay() {
        assertArrayEquals(
                new int[] {1, 2, 3},
                toArray(sol.deleteDuplicates(list(1, 2, 3))));
    }

    @Test
    void singleNodeStays() {
        assertArrayEquals(new int[] {1}, toArray(sol.deleteDuplicates(list(1))));
    }

    @Test
    void emptyList() {
        assertNull(sol.deleteDuplicates(null));
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

    private static int[] toArray(ListNode head) {
        int n = 0;
        for (ListNode p = head; p != null; p = p.next) {
            n++;
        }
        int[] out = new int[n];
        int i = 0;
        for (ListNode p = head; p != null; p = p.next) {
            out[i++] = p.val;
        }
        return out;
    }
}
