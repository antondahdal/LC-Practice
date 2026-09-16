package test.week05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.ListNode;
import questions.week05.AddTwoNumbers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AddTwoNumbersTest {

    private AddTwoNumbers sol;

    @BeforeEach
    void setUp() {
        sol = new AddTwoNumbers();
    }

    @Test
    void addsSameLengthLists() {
        assertArrayEquals(
                new int[] {7, 0, 8},
                toArray(sol.addTwoNumbers(list(2, 4, 3), list(5, 6, 4))));
    }

    @Test
    void carriesIntoANewDigit() {
        assertArrayEquals(
                new int[] {0, 0, 0, 1},
                toArray(sol.addTwoNumbers(list(9, 9, 9), list(1))));
    }

    @Test
    void handlesDifferentLengths() {
        assertArrayEquals(
                new int[] {9, 9, 9, 9},
                toArray(sol.addTwoNumbers(list(9, 9), list(0, 0, 9, 9))));
    }

    @Test
    void addsZeroes() {
        assertArrayEquals(
                new int[] {0},
                toArray(sol.addTwoNumbers(list(0), list(0))));
    }

    private static ListNode list(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int val : vals) {
            cur.next = new ListNode(val);
            cur = cur.next;
        }
        return dummy.next;
    }

    private static int[] toArray(ListNode head) {
        int size = 0;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            size++;
        }

        int[] values = new int[size];
        int index = 0;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            values[index++] = cur.val;
        }
        return values;
    }
}
