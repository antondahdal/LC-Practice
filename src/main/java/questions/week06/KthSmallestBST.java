package questions.week06;

import questions.common.TreeNode;

/**
 * 230. Kth Smallest Element in a BST
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 *
 * Given the root of a binary search tree and an integer k, return the kth
 * smallest value (1-indexed) of all the values of the nodes in the tree.
 *
 * Example: 3 / 1 4 / null 2, k = 1 → 1.
 * Example: 5 / 3 6 / 2 4 / 1, k = 3 → 3.
 */
public class KthSmallestBST {
    int counter=0;

    public int kthSmallest(TreeNode root, int k) {
        return  inorder(root,k);
    }
    int inorder(TreeNode node,int k) {
       
        if (node == null) return 0;
        int left = inorder(node.left, k);   
        if (counter == k) return left;// left
        counter++;
        if (counter==k) return node.val;
        int right =inorder(node.right,k);   
        if (counter == k) return right;
        return 0;  // right
    }
}
