package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.LinkedListCycle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListCycleTest {

    private LinkedListCycle sol;

    @BeforeEach
    void setUp() {
        sol = new LinkedListCycle();
    }

    @Test
    void cycleNotAtHead() {
        ListNode head = list(3, 2, 0, -4);
        tail(head).next = head.next;
        assertTrue(sol.hasCycle(head));
    }

    @Test
    void cycleAtHead() {
        ListNode head = list(1, 2);
        tail(head).next = head;
        assertTrue(sol.hasCycle(head));
    }

    @Test
    void noCycle() {
        assertFalse(sol.hasCycle(list(1, 2)));
    }

    @Test
    void noCycleThreeNodes() {
        assertFalse(sol.hasCycle(list(1, 2, 3)));
    }

    @Test
    void singleNodeNoCycle() {
        assertFalse(sol.hasCycle(list(1)));
    }

    @Test
    void singleNodeCycle() {
        ListNode head = list(1);
        head.next = head;
        assertTrue(sol.hasCycle(head));
    }

    @Test
    void emptyList() {
        assertFalse(sol.hasCycle(null));
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

    private static ListNode tail(ListNode head) {
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
        }
        return p;
    }
}
