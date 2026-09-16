package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.MergeTwoLists;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MergeTwoListsTest {

    private MergeTwoLists sol;

    @BeforeEach
    void setUp() {
        sol = new MergeTwoLists();
    }

    @Test
    void mergesTwoSortedLists() {
        assertArrayEquals(
                new int[] {1, 1, 2, 3, 4, 4},
                toArray(sol.mergeTwoLists(list(1, 2, 4), list(1, 3, 4))));
    }

    @Test
    void bothEmpty() {
        assertNull(sol.mergeTwoLists(null, null));
    }

    @Test
    void oneEmpty() {
        assertArrayEquals(new int[] {0}, toArray(sol.mergeTwoLists(null, list(0))));
    }

    @Test
    void leftoverOnOneSide() {
        assertArrayEquals(
                new int[] {1, 2, 3, 4, 5},
                toArray(sol.mergeTwoLists(list(1, 2), list(3, 4, 5))));
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
