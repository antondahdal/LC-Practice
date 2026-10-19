package questions.week10;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 215. Kth Largest Element in an Array
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Given an int array nums and an int k, return the kth largest element.
 * Kth largest in sorted order, not the kth distinct element.
 *
 * Example: [3,2,1,5,6,4], k = 2 -> 5. [3,2,3,1,2,4,5,5,6], k = 4 -> 4.
 */
public class KthLargest {

    public int findKthLargest(int[] nums, int k) {
        if(nums.length==0|| nums.length<k) return -1;
        int ans=0;
        Queue heap=new PriorityQueue<Integer>();
            for(int x:nums){
                heap.add(x);
                if(heap.size()>k){
                heap.remove();
                }
            }
        return Integer.valueOf( heap.remove().toString());
       
    }

    public int findKthLargestNLogK(int[] nums, int k) {
        if(nums.length==0|| nums.length<k) return -1;
        int ans=0;
        Queue heap=new PriorityQueue<Integer>();
        for(int x:nums){
            heap.add(x);
            if(heap.size()>k){
                heap.remove();
            }
        }
        ans= Integer.valueOf( heap.remove().toString());
        return ans;
    }
}
