package questions.week06;

import java.util.LinkedList;
import java.util.Queue;

import questions.common.TreeNode;

/**
 * 101. Symmetric Tree
 * https://leetcode.com/problems/symmetric-tree/
 *
 * Given the root of a binary tree, check whether it is a mirror of itself
 * (symmetric around its center).
 *
 * Example: 1 / 2 2 / 3 4 4 3 → true
 * Example: 1 / 2 2 / null 3 null 3 → false
 */
public class SymmetricTree {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> left = new LinkedList<>();
        Queue<TreeNode> right = new LinkedList<>();
        left.add(root.left);
        right.add(root.right);

        while (!left.isEmpty()) {
            TreeNode leftNode = left.remove();
            TreeNode rightNode = right.remove();

            if (leftNode == null && rightNode == null) continue;
            if (leftNode == null || rightNode == null) return false;
            if (leftNode.val != rightNode.val) return false;

            left.add(leftNode.left);
            right.add(rightNode.right);
            left.add(leftNode.right);
            right.add(rightNode.left);
        }
        return true;
    }
}
