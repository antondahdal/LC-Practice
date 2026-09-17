package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.IntersectionOfTwoLinkedLists;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class IntersectionOfTwoLinkedListsTest {

    private IntersectionOfTwoLinkedLists sol;

    @BeforeEach
    void setUp() {
        sol = new IntersectionOfTwoLinkedLists();
    }

    @Test
    void meetsOnASharedTail() {
        ListNode shared = list(8, 4, 5);
        ListNode a = attach(list(4, 1), shared);
        ListNode b = attach(list(5, 6, 1), shared);
        assertSame(shared, sol.getIntersectionNode(a, b));
    }

    @Test
    void meetsAtTheHeadOfA() {
        ListNode shared = list(1, 2, 3);
        ListNode b = attach(list(9, 9), shared);
        assertSame(shared, sol.getIntersectionNode(shared, b));
    }

    @Test
    void noIntersection() {
        assertNull(sol.getIntersectionNode(list(2, 6, 4), list(1, 5)));
    }

    @Test
    void sameValuesButDifferentNodes() {
        assertNull(sol.getIntersectionNode(list(1, 2, 3), list(1, 2, 3)));
    }

    @Test
    void oneEmpty() {
        assertNull(sol.getIntersectionNode(null, list(1)));
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

    private static ListNode attach(ListNode head, ListNode tail) {
        if (head == null) {
            return tail;
        }
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = tail;
        return head;
    }
}
