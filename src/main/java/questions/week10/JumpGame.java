package questions.week10;

/**
 * 55. Jump Game
 * https://leetcode.com/problems/jump-game/
 *
 * You start at index 0. nums[i] is the farthest jump you may take from i.
 * Return true if you can reach the last index.
 *
 * Example: [2,3,1,1,4] -> true. [3,2,1,0,4] -> false.
 */
public class JumpGame {

    public boolean canJump(int[] nums) {
        boolean flag=false;
       int indx=0;
        if(nums.length==0) return false;
    
        for(int i=0;i<nums.length;i++){
            if(i>indx) break;
            int maxInt=whichToCOntinue(i,nums);
            if(maxInt>indx) indx=maxInt;
            if(indx>=nums.length-1){
                flag= true;
                break;
            }
        }
        return flag;
    }

    private int whichToCOntinue(int min,int[] nums){
        int maxInt=min;
        int end=min+nums[min];
        if(end>=nums.length) end=nums.length-1;
        for(int i=min;i<=end;i++){
            int reach=i+nums[i];
            if(reach>maxInt){
                maxInt=reach;
            }
        }
        return maxInt;
    }
}
