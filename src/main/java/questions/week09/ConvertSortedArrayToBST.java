package questions.week09;

import questions.common.TreeNode;

/**
 * 108. Convert Sorted Array to Binary Search Tree
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 *
 * nums is sorted ascending, and every value is unique.
 * Build a height-balanced binary search tree from it.
 * More than one tree can be correct.
 *
 * Example: [-10, -3, 0, 5, 9]
 */
public class ConvertSortedArrayToBST {

    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left + 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, left, mid - 1);
        root.right = build(nums, mid + 1, right);
        return root;
    }
}
