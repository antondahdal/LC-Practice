package questions.week05;

import questions.common.ListNode;

/**
 * 21. Merge Two Sorted Lists
 * https://leetcode.com/problems/merge-two-sorted-lists/
 *
 * Merge two sorted linked lists into one sorted list.
 * Return the head of the merged list.
 *
 * Example: 1 -> 2 -> 4 and 1 -> 3 -> 4 becomes 1 -> 1 -> 2 -> 3 -> 4 -> 4.
 */
public class MergeTwoLists {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null&&list2==null) return null;
        ListNode finalListTmp=new ListNode();
        ListNode finalList=finalListTmp;
        while(list1!=null||list2!=null){
        
            
            if(list1!=null&&list2!=null){
                if(list1.val<list2.val){
                    finalList.next=new ListNode(list1.val);
                    finalList=finalList.next;
                list1=list1.next;}
                else {
                    finalList.next=new ListNode(list2.val);
                    finalList=finalList.next;
                    list2=list2.next;
                }
              
            }
           
             if(list1==null&&list2!=null){
                finalList.next=new ListNode(list2.val);
                finalList=finalList.next;
                list2=list2.next;
            }
            if(list2==null&&list1!=null){
                finalList.next=new ListNode(list1.val);
                finalList=finalList.next;
                list1=list1.next;
            }
         
           
        }
        return finalListTmp.next;
    }
}
