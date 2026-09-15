package questions.week05;

import questions.common.ListNode;

/**
 * 143. Reorder List
 * https://leetcode.com/problems/reorder-list/
 *
 * Given the head of a singly linked list, reorder the nodes as
 * first, last, second, second-last, and so on. Change links, not values.
 *
 * Example: 1 -> 2 -> 3 -> 4  becomes  1 -> 4 -> 2 -> 3
 */
public class ReorderList {

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // 1) Find the middle. slow stops at the end of the front half.
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2) Cut, then reverse the back half. Same loop as #206.
        ListNode sec = slow.next;
        slow.next = null;
        ListNode first = null;
        while (sec != null) {
            ListNode tmp = sec.next;
            sec.next = first;
            first = sec;
            sec = tmp;
        }

        // 3) Hook: one from the front half, one from the reversed back half.
        ListNode front = head;
        ListNode back = first;
        while (back != null) {
            ListNode frontNext = front.next;
            ListNode backNext = back.next;
            front.next = back;
            back.next = frontNext;
            front = frontNext;
            back = backNext;
        }
    }
}
