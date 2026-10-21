package questions.week10;

/**
 * 45. Jump Game II
 * https://leetcode.com/problems/jump-game-ii/
 *
 * nums[i] is the farthest jump you may take from index i.
 * You start at index 0. You can always reach the last index.
 * Return the fewest jumps that land on the last index.
 *
 * Example: [2,3,1,1,4] -> 2.
 *
 * Coach-written, untimed (2026-10-21).
 */
public class JumpGameII {

    public int jump(int[] nums) {
        int jumps = 0;
        int end = 0;
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }
}
