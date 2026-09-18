package questions.week03;

/**
 * 238. Product of Array Except Self
 * https://leetcode.com/problems/product-of-array-except-self/
 *
 * Each slot = product of everyone else. No division.
 *
 * Example: [1,2,3,4] -> [24,12,8,6]
 */
public class ProductOfArrayExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        int n=nums.length;
        int[] out=new int[n];
        out[0]=1;
        for(int i=1;i<n;i++){
            out[i]=out[i-1]*nums[i-1];
        }
        int right=1;
        for(int i=n-1;i>=0;i--){
            out[i]=out[i]*right;
            right=right*nums[i];
        }
        return out;
    }
}
