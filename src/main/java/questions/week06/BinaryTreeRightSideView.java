package questions.week06;

import questions.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 199. Binary Tree Right Side View
 * https://leetcode.com/problems/binary-tree-right-side-view/
 *
 * Given the root of a binary tree, imagine yourself standing on the right side
 * of it. Return the values of the nodes you can see ordered from top to bottom.
 *
 * Example: 1 / 2 3 / null 5 null 4 → [1, 3, 4]
 */
public class BinaryTreeRightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<Integer> retList = new ArrayList<>();
        if (root == null) return retList;

        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.remove();
                if (i == size - 1) {
                    retList.add(currNode.val);
                }
                if (currNode.left != null) {
                    queue.add(currNode.left);
                }
                if (currNode.right != null) {
                    queue.add(currNode.right);
                }
            }
        }

        return retList;
    }
}
