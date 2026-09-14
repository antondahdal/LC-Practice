package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.RemoveNthNodeFromEnd;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RemoveNthNodeFromEndTest {

    private RemoveNthNodeFromEnd sol;

    @BeforeEach
    void setUp() {
        sol = new RemoveNthNodeFromEnd();
    }

    @Test
    void removeSecondFromEnd() {
        ListNode head = list(1, 2, 3, 4, 5);
        assertArrayEquals(new int[] {1, 2, 3, 5}, toArray(sol.removeNthFromEnd(head, 2)));
    }

    @Test
    void singleNode() {
        ListNode head = list(1);
        assertNull(sol.removeNthFromEnd(head, 1));
    }

    @Test
    void twoNodesRemoveLast() {
        ListNode head = list(1, 2);
        assertArrayEquals(new int[] {1}, toArray(sol.removeNthFromEnd(head, 1)));
    }

    @Test
    void twoNodesRemoveFirst() {
        ListNode head = list(1, 2);
        assertArrayEquals(new int[] {2}, toArray(sol.removeNthFromEnd(head, 2)));
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
