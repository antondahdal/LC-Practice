package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.ReverseLinkedListII;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReverseLinkedListIITest {

    private ReverseLinkedListII sol;

    @BeforeEach
    void setUp() {
        sol = new ReverseLinkedListII();
    }

    @Test
    void reversesAMiddleSlice() {
        assertArrayEquals(
                new int[] {1, 4, 3, 2, 5},
                toArray(sol.reverseBetween(list(1, 2, 3, 4, 5), 2, 4)));
    }

    @Test
    void reversesFromTheHead() {
        assertArrayEquals(
                new int[] {5, 4, 3, 2, 1},
                toArray(sol.reverseBetween(list(1, 2, 3, 4, 5), 1, 5)));
    }

    @Test
    void leftEqualsRight() {
        assertArrayEquals(
                new int[] {1, 2, 3},
                toArray(sol.reverseBetween(list(1, 2, 3), 2, 2)));
    }

    @Test
    void singleNode() {
        assertArrayEquals(new int[] {5}, toArray(sol.reverseBetween(list(5), 1, 1)));
    }

    @Test
    void emptyList() {
        assertNull(sol.reverseBetween(null, 1, 1));
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
