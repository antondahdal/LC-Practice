package questions.week05;

import questions.common.ListNode;

/**
 * 82. Remove Duplicates from Sorted List II
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 *
 * The list is sorted. Delete every value that appears more than once.
 * Keep only numbers that showed up once. Return the new head.
 *
 * Example: 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 becomes 1 -> 2 -> 5.
 */
public class RemoveDuplicatesSortedListII {

    public ListNode deleteDuplicates(ListNode head) {
        if (head==null || head.next==null) return head;
        ListNode dummy=new ListNode(0, head);
        ListNode left=dummy;
        ListNode right=head;
        while(right!=null){
            int val=right.val;
            if(right.next!=null&&val==right.next.val){
            
                while(right!=null &&right.val==val){
                    right=right.next;
                }
                left.next = right;
        }
        else{
           
            left=right;
            right=right.next;
        }
    
    }
    return dummy.next;
}
}
