package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.ReorderList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReorderListTest {

    private ReorderList sol;

    @BeforeEach
    void setUp() {
        sol = new ReorderList();
    }

    @Test
    void fourNodes() {
        ListNode head = list(1, 2, 3, 4);
        sol.reorderList(head);
        assertArrayEquals(new int[] {1, 4, 2, 3}, toArray(head));
    }

    @Test
    void fiveNodes() {
        ListNode head = list(1, 2, 3, 4, 5);
        sol.reorderList(head);
        assertArrayEquals(new int[] {1, 5, 2, 4, 3}, toArray(head));
    }

    @Test
    void threeNodes() {
        ListNode head = list(1, 2, 3);
        sol.reorderList(head);
        assertArrayEquals(new int[] {1, 3, 2}, toArray(head));
    }

    @Test
    void twoNodes() {
        ListNode head = list(1, 2);
        sol.reorderList(head);
        assertArrayEquals(new int[] {1, 2}, toArray(head));
    }

    @Test
    void singleNode() {
        ListNode head = list(1);
        sol.reorderList(head);
        assertArrayEquals(new int[] {1}, toArray(head));
    }

    @Test
    void emptyList() {
        assertDoesNotThrow(() -> sol.reorderList(null));
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
