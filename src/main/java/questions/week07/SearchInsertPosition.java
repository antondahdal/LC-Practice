package questions.week07;

/**
 * 35. Search Insert Position
 * https://leetcode.com/problems/search-insert-position/
 *
 * Given a sorted array of distinct ints and a target, return the index of target.
 * If it is not there, return the index where it would be inserted to keep the order.
 * Must run in O(log n).
 *
 * Example: [1,3,5,6], 5 -> 2. [1,3,5,6], 2 -> 1. [1,3,5,6], 7 -> 4.
 */
public class SearchInsertPosition {

    public int searchInsert(int[] nums, int target) {
        if (nums==null||nums.length==0) return -1;
        if(nums[nums.length-1]<target) return nums.length;
        int low=0,high=nums.length-1;
       while(low<=high){
        int mid = low + (high - low) / 2;
            if(nums[mid]==target) return mid;
            else if(nums[mid]<target) low=mid+1;
            else high= mid-1;

        }
        return low;
    }
}
