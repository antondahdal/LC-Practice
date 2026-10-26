package questions.week11;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 189. Rotate Array
 * https://leetcode.com/problems/rotate-array/
 *
 * Rotate nums to the right by k steps. Change nums itself.
 *
 * Example: [1,2,3,4,5,6,7], k = 3 -> [5,6,7,1,2,3,4].
 */
public class RotateArray {

    public void rotate(int[] nums, int k) {
        if(nums.length<=1) return;
        if(k>nums.length) k=k%nums.length;
        int left = 0;
        int right = nums.length - 1;


        while (left < right) {
            // Swap elements
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            // Move pointers toward the center
            left++;
            right--;
        }
        left=0;
        right=k-1;

        while (left < right) {
            // Swap elements
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            // Move pointers toward the center
            left++;
            right--;
        }
        left=k;
        right= nums.length - 1;
        while (left < right) {
            // Swap elements
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            // Move pointers toward the center
            left++;
            right--;
        }

    }
}
