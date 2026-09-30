package questions.week07;

/**
 * 33. Search in Rotated Sorted Array
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 *
 * A sorted array of unique ints was rotated at an unknown index.
 * Return the index of target, or -1 if it is not there.
 * Must run in O(log n).
 *
 * Example: [4,5,6,7,0,1,2], target 0 -> 4.
 * [4,5,6,7,0,1,2], target 3 -> -1.
 * [1], target 0 -> -1.
 */
public class SearchRotatedSortedArray {

    public int search(int[] nums, int target) {
        if (nums==null||nums.length==0) return -1;
       
        int low=0,high=nums.length-1;
       while(low<=high){
        int mid = low + (high - low) / 2;
            if(nums[mid]==target) return mid;
            if (nums[low] <= nums[mid]) {
                if (target >= nums[low] && target < nums[mid]) high = mid - 1;
                else low = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[high]) low = mid + 1;
                else high = mid - 1;
            }}
        return -1;
    }
}
