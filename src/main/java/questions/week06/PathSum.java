package questions.week06;

import questions.common.TreeNode;

/**
 * 112. Path Sum
 * https://leetcode.com/problems/path-sum/
 *
 * Given the root of a binary tree and an integer targetSum, return true if the
 * tree has a root-to-leaf path such that adding up all the values along the
 * path equals targetSum. A leaf is a node with no children.
 *
 * Example: 5 / 4 8 / 11 13 4 / 7 2   1, targetSum = 22 → true (5-4-11-2).
 */
public class PathSum {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }

    private boolean dfs(TreeNode root, int remaining) {
        if (root == null) return false;

        remaining -= root.val;

        if (root.left == null && root.right == null) {
            return remaining == 0;
        }

        return dfs(root.left, remaining) || dfs(root.right, remaining);
    }
}
