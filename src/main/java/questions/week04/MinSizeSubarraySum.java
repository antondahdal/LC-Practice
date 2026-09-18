package questions.week04;

/**
 * 209. Minimum Size Subarray Sum
 * https://leetcode.com/problems/minimum-size-subarray-sum/
 *
 * Positive nums. Shortest contiguous stretch whose sum >= target. Else 0.
 *
 * Example: target=7, [2,3,1,2,4,3] -> 2
 */
public class MinSizeSubarraySum {

    public int minSubArrayLen(int target, int[] nums) {
        int left=0;
        int sum=0;
        int best=Integer.MAX_VALUE;
        for(int right=0;right<nums.length;right++){
            sum=sum+nums[right];
            while(sum>=target){
                int len=right-left+1;
                if(len<best) best=len;
                sum=sum-nums[left];
                left++;
            }
        }
        if(best==Integer.MAX_VALUE) return 0;
        return best;
    }
}
