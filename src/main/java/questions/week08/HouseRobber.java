package questions.week08;

/**
 * 198. House Robber
 * https://leetcode.com/problems/house-robber/
 *
 * Houses stand in a row; nums[i] is the money in house i.
 * You cannot take from two houses that are next to each other.
 * Return the most money you can take.
 *
 * Example: nums = [2, 7, 9, 3, 1] -> 12 (houses 0, 2, 4: 2 + 9 + 1).
 */
public class HouseRobber {

    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        int[] best = new int[nums.length];
        best[0] = nums[0];
        best[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            best[i] = Math.max(best[i - 1], nums[i] + best[i - 2]);
        }
        return best[nums.length - 1];
    }
}
