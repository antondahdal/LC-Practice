package questions.week03;

/**
 * 26. Remove Duplicates from Sorted Array
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 *
 * Sorted. Write uniques to the front. Return how many uniques. In place.
 *
 * Example: [1,1,2] -> k=2, first slots [1,2]
 */
public class RemoveDuplicatesSortedArray {

    public int removeDuplicates(int[] nums) {
        if(nums.length==0) return 0;
        int write=0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]!=nums[write]){
                write++;
                nums[write]=nums[i];
            }
        }
        return write+1;
    }
}
