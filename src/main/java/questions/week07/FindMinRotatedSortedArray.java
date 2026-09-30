package questions.week07;

/**
 * 153. Find Minimum in Rotated Sorted Array
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * A sorted array of unique ints was rotated between 1 and n times.
 * Return the smallest element.
 * Must run in O(log n).
 *
 * Example: [3,4,5,1,2] -> 1.
 * [4,5,6,7,0,1,2] -> 0.
 * [11,13,15,17] -> 11 (rotated n times, back to sorted).
 */
public class FindMinRotatedSortedArray {

    public int findMin(int[] nums) {
        if (nums.length==0) return 0;
        int low=0;
        int high=nums.length-1;
        while (low<high){
            int mid =low+(high-low) /2 ;
            if(nums[mid]>nums[high]) {
                low=mid+1;
            }
            else {
                high=mid;
            }

        }
       
        return  nums[low];
    }
}
