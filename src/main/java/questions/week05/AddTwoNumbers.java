package questions.week05;

import questions.common.ListNode;

/**
 * 2. Add Two Numbers
 * https://leetcode.com/problems/add-two-numbers/
 *
 * Each linked list stores one non-negative integer in reverse digit order.
 * Return their sum using the same representation.
 *
 * Example: 2 -> 4 -> 3 plus 5 -> 6 -> 4 becomes 7 -> 0 -> 8.
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }

        return dummy.next;
    }
}
