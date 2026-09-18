package questions.week03;

/**
 * 167. Two Sum II - Input Array Is Sorted
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * Sorted array. Find two different indexes that sum to target.
 * Return 1-based indexes, smaller first. O(1) extra space.
 *
 * Example: [2,7,11,15], target 9 -> [1,2]
 */
public class TwoSumII {

    public int[] twoSum(int[] numbers, int target) {
        int left=0;
        int right=numbers.length-1;
        while(left<right){
            int sum=numbers[left]+numbers[right];
            if(sum==target){
                return new int[]{left+1,right+1};
            }
            else if(sum<target){
                left++;
            }
            else{
                right--;
            }
        }
        return new int[]{-1,-1};
    }
}
