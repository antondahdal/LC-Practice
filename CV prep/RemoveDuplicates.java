/**
 * 26. Remove Duplicates from Sorted Array
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 *
 * nums is sorted. Remove duplicates in place.
 * Return k, the number of unique values.
 * The first k elements are those unique values, in order.
 */
public class RemoveDuplicates {

    public int removeDuplicates(int[] nums) {
       if(nums.length<=1) return 0;
        int slow=0;
        int fast=1;

        while(fast<=nums.length-1){

            if(nums[slow]==nums[fast]) fast++;
            else {
                slow++;
                nums[slow]=nums[fast];
                fast++;
            }
        }



        return slow+1;
    

    }
}
