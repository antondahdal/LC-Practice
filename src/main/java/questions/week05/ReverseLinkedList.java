package questions.week05;

import questions.common.ListNode;

/**
 * 206. Reverse Linked List
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * Given the head of a singly linked list, reverse the list and return the new head.
 *
 * Example: 1 -> 2 -> 3 -> 4 -> 5  becomes  5 -> 4 -> 3 -> 2 -> 1
 */
public class ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null) return head;

        ListNode first=null;
        ListNode sec=head;
        while(sec!=null ){
            ListNode tmp=sec.next;
            sec.next=first;
            first=sec;
            sec=tmp;
        }
        return first;
    }
}
