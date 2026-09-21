package questions.week06;

import questions.common.TreeNode;

/**
 * 104. Maximum Depth of Binary Tree
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * Given the root of a binary tree, return its maximum depth.
 * Depth is the number of nodes along the longest path from the root
 * down to the farthest leaf.
 *
 * Example: 3 / 9 20 / 15 7 → 3
 */
public class MaxDepthBinaryTree {

    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        int left=maxDepth(root.left);
        int right=maxDepth(root.right);
       
      
        return Math.max(left, right)+1 ;

    }
}
