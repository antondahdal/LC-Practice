package questions.week06;

import questions.common.TreeNode;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * Given a binary tree and two nodes p and q in it, return their lowest common
 * ancestor: the deepest node that has both p and q as descendants.
 * A node counts as a descendant of itself.
 * All values are unique. p != q. Both exist in the tree.
 *
 * Example: 3 / 5 1 / 6 2 0 8 / null null 7 4
 *   p = 5, q = 1 → 3
 *   p = 5, q = 4 → 5
 */
public class LowestCommonAncestor {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
}
