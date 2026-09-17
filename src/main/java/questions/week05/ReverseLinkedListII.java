package questions.week05;

import questions.common.ListNode;

/**
 * 92. Reverse Linked List II
 * https://leetcode.com/problems/reverse-linked-list-ii/
 *
 * Reverse the nodes from position left to position right (1-indexed).
 * Leave the rest of the list as it is. Return the head.
 *
 * Example: 1 -> 2 -> 3 -> 4 -> 5, left = 2, right = 4 becomes 1 -> 4 -> 3 -> 2 -> 5.
 */
public class ReverseLinkedListII {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0, head);
        ListNode before = dummy;
        for (int i = 1; i < left; i++) {
            before = before.next;
        }

        ListNode start = before.next;
        ListNode first = null;
        ListNode sec = start;
        for (int i = 0; i < right - left + 1; i++) {
            ListNode tmp = sec.next;
            sec.next = first;
            first = sec;
            sec = tmp;
        }

        before.next = first;
        start.next = sec;
        return dummy.next;
    }
}
