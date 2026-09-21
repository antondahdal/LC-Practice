package questions.week06;

import questions.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 102. Binary Tree Level Order Traversal
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 *
 * Given the root of a binary tree, return the level order traversal of its
 * nodes' values (left to right, level by level).
 *
 * Example: 3 / 9 20 / 15 7 → [[3], [9, 20], [15, 7]]
 */
public class LevelOrderTraversal {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> answer = new ArrayList<List<Integer>>();
        if (root == null) return answer;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> tmpArr = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = queue.remove();
                tmpArr.add(tmp.val);
                if (tmp.left != null) {
                    queue.add(tmp.left);
                }
                if (tmp.right != null) {
                    queue.add(tmp.right);
                }
            }
            answer.add(tmpArr);
        }

        return answer;
    }
}
