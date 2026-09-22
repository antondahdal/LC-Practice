package questions.week06;

import questions.common.TreeNode;

/**
 * 98. Validate Binary Search Tree
 * https://leetcode.com/problems/validate-binary-search-tree/
 *
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST: every node in the left subtree is less than the node, every node
 * in the right subtree is greater, and both subtrees are valid BSTs.
 *
 * Example: 2 / 1 3 → true. 5 / 1 4 / 3 6 → false (3 sits on the right of 5).
 */
public class ValidateBST {

    public boolean isValidBST(TreeNode root) {
       
        
     
      return check(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }

    private boolean check(TreeNode root,long low,long max){

        if (root==null) return true;
        if (root.val <= low || root.val >= max) return false;
           return check(root.right,root.val, max) && 
           check(root.left,low, root.val) ;
         
        }
    
}
