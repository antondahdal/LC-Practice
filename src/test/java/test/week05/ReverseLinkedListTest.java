package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.ReverseLinkedList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReverseLinkedListTest {

    private ReverseLinkedList sol;

    @BeforeEach
    void setUp() {
        sol = new ReverseLinkedList();
    }

    @Test
    void fiveNodes() {
        ListNode head = list(1, 2, 3, 4, 5);
        assertArrayEquals(new int[] {5, 4, 3, 2, 1}, toArray(sol.reverseList(head)));
    }

    @Test
    void twoNodes() {
        ListNode head = list(1, 2);
        assertArrayEquals(new int[] {2, 1}, toArray(sol.reverseList(head)));
    }

    @Test
    void singleNode() {
        ListNode head = list(1);
        assertArrayEquals(new int[] {1}, toArray(sol.reverseList(head)));
    }

    @Test
    void emptyList() {
        assertNull(sol.reverseList(null));
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
