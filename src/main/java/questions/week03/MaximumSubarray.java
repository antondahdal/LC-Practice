package questions.week03;

/**
 * 53. Maximum Subarray
 * https://leetcode.com/problems/maximum-subarray/
 *
 * Contiguous stretch with the biggest sum.
 *
 * Example: [-2,1,-3,4,-1,2,1,-5,4] -> 6  ([4,-1,2,1]
 */
public class MaximumSubarray {

    public int maxSubArray(int[] nums) {
        int current=nums[0];
        int best=nums[0];
        for(int i=1;i<nums.length;i++){
            if(current+nums[i]>nums[i]){
                current=current+nums[i];
            }
            else{
                current=nums[i];
            }
            if(current>best){
                best=current;
            }
        }
        return best;
    }
}
