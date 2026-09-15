package questions.week05;

import java.util.HashSet;
import java.util.Set;

import questions.common.ListNode;

/**
 * 141. Linked List Cycle
 * https://leetcode.com/problems/linked-list-cycle/
 *
 * Return true if some node can be reached again by following next.
 * Return false if the list ends.
 */
public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        if(head==null||head.next==null) return false;
        ListNode slow=head;
        ListNode fast=head.next.next;
      while(fast!=null){
      if(slow==fast) return true;
      slow=slow.next;
      if(fast.next == null|| fast.next.next==null )return false;
      fast=fast.next.next;
      }
        return false;
    }
}
