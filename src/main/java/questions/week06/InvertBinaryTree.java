package questions.week06;

import questions.common.TreeNode;

/**
 * 226. Invert Binary Tree
 * https://leetcode.com/problems/invert-binary-tree/
 *
 * Given the root of a binary tree, invert the tree and return its root.
 * Swap every left and right child, all the way down.
 *
 * Example: 4 / 2 7 / 1 3 6 9 → 4 / 7 2 / 9 6 3 1
 */
public class InvertBinaryTree {

    public TreeNode invertTree(TreeNode root) {
        if (root==null) return null;

        TreeNode tmp=root.right;
        root.right=root.left;
        root.left=tmp;
        invertTree( root.left);
        invertTree( root.right);
        return root;
    }
}
