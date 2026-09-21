package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.InvertBinaryTree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvertBinaryTreeTest {

    private InvertBinaryTree sol;

    @BeforeEach
    void setUp() {
        sol = new InvertBinaryTree();
    }

    @Test
    void exampleTree() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        TreeNode out = sol.invertTree(root);
        assertArrayEquals(new Integer[] {4, 7, 2, 9, 6, 3, 1}, toLevelOrder(out));
    }

    @Test
    void emptyTree() {
        assertNull(sol.invertTree(null));
    }

    @Test
    void singleNode() {
        TreeNode root = new TreeNode(1);
        TreeNode out = sol.invertTree(root);
        assertArrayEquals(new Integer[] {1}, toLevelOrder(out));
    }

    @Test
    void twoNodes() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode out = sol.invertTree(root);
        assertArrayEquals(new Integer[] {1, null, 2}, toLevelOrder(out));
    }

    private static Integer[] toLevelOrder(TreeNode root) {
        if (root == null) {
            return new Integer[0];
        }
        java.util.ArrayList<Integer> vals = new java.util.ArrayList<>();
        java.util.Queue<TreeNode> q = new java.util.LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (n == null) {
                vals.add(null);
                continue;
            }
            vals.add(n.val);
            if (n.left != null || n.right != null) {
                q.add(n.left);
                q.add(n.right);
            }
        }
        while (!vals.isEmpty() && vals.get(vals.size() - 1) == null) {
            vals.remove(vals.size() - 1);
        }
        return vals.toArray(new Integer[0]);
    }
}
