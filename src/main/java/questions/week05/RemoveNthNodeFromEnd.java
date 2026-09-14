package questions.week05;

import questions.common.ListNode;

/**
 * 19. Remove Nth Node From End of List
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 *
 * Given the head of a linked list, remove the nth node from the end
 * and return the head of the list.
 *
 * Example: 1 -> 2 -> 3 -> 4 -> 5, n = 2 -> 1 -> 2 -> 3 -> 5
 */
public class RemoveNthNodeFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {
       
        ListNode first=head;

        ListNode back=head;
         for(int i=0;i<n;i++){
            first=first.next;
        }
        if(first==null){
          
            return back.next;
        }
        while(first!=null && first.next!=null){
            first=first.next;
            back=back.next;
        }
        back.next=back.next.next;
        return head;
    }
}
