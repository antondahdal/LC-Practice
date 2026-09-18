package questions.week05;

import questions.common.ListNode;

/**
 * 234. Palindrome Linked List
 * https://leetcode.com/problems/palindrome-linked-list/
 *
 * Return true if the list reads the same forward and backward.
 *
 * Example: 1 -> 2 -> 2 -> 1 is true. 1 -> 2 is false.
 */
public class PalindromeLinkedList {

    public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null) return true;
        ListNode slow=head;
        ListNode fast=head;
        while(fast.next != null && fast.next.next != null){
            slow=slow.next;
            fast=fast.next.next;
        }
        ListNode before=null;
        slow=slow.next;
        while(slow!=null){
            ListNode tmp=slow.next;
            slow.next=before;
            before=slow;
            slow=tmp;
        }
        while(before!=null){
            if (head.val!=before.val){
                return false;
            }
            before=before.next;
            head=head.next;
        }

        return true;
    }
}
