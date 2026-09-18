package questions.week03;

/**
 * 283. Move Zeroes
 * https://leetcode.com/problems/move-zeroes/
 *
 * Move all 0s to the end. Keep other numbers in the same order. In place.
 *
 * Example: [0,1,0,3,12] -> [1,3,12,0,0]
 */
public class MoveZeroes {

    public void moveZeroes(int[] nums) {
        int write=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                nums[write]=nums[i];
                write++;
            }
        }
        while(write<nums.length){
            nums[write]=0;
            write++;
        }
    }
}
