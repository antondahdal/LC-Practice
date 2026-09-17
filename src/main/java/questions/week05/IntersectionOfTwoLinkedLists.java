package questions.week05;

import java.util.HashSet;

import questions.common.ListNode;

/**
 * 160. Intersection of Two Linked Lists
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 *
 * Two lists may join at one shared node and then share the rest of the tail.
 * Return that first shared node, or null if they never join.
 *
 * Example: A is 4 -> 1 -> 8 -> 4 -> 5 and B is 5 -> 6 -> 1 -> 8 -> 4 -> 5,
 * and they share the node with value 8. Return that node, not a copy.
 */
public class IntersectionOfTwoLinkedLists {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null || headB==null) return null;
        HashSet<ListNode> set=new HashSet<>();
        while (headA!=null||headB!=null){
            if(set.contains(headA)){
                return headA;
            }if(set.contains(headB)){
                return headB;
            }
            else{
                set.add(headB);
                set.add(headA);
               
            }
            if(headA!=null)
                headA=headA.next;
          
           if(headB!=null) 
                headB=headB.next;
        }
        return null;
    }
}
