package questions.week07;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 215. Kth Largest Element in an Array
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Given an int array nums and an int k, return the kth largest element.
 * Kth largest in sorted order, not kth distinct.
 * Try to solve it without sorting the whole array.
 *
 * Example: [3,2,1,5,6,4], k = 2 -> 5. [3,2,3,1,2,4,5,5,6], k = 4 -> 4.
 */
public class KthLargest {

    public int findKthLargest(int[] nums, int k) {
        if(nums.length==0||k>nums.length) return 0;
        if(nums.length==1) return nums[0];
        int retVal=0;
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
   for(int x:nums){
max.offer(x);
   }
   
        for(int i=0;i<k;i++){
            int tmp=max.poll();
            if(i==k-1){
                
             retVal=tmp;
             System.out.println(retVal);

        }

    }
    return retVal;

}
}
