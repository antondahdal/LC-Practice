package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.PathSum;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathSumTest {

    private PathSum sol;

    @BeforeEach
    void setUp() {
        sol = new PathSum();
    }

    @Test
    void exampleHasPath() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);
        assertTrue(sol.hasPathSum(root, 22));
    }

    @Test
    void exampleNoPath() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        assertFalse(sol.hasPathSum(root, 5));
    }

    @Test
    void emptyTree() {
        assertFalse(sol.hasPathSum(null, 0));
    }

    @Test
    void singleNodeMatch() {
        assertTrue(sol.hasPathSum(new TreeNode(1), 1));
    }

    @Test
    void singleNodeMiss() {
        assertFalse(sol.hasPathSum(new TreeNode(1), 0));
    }

    @Test
    void mustReachLeaf() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        assertFalse(sol.hasPathSum(root, 1));
    }
}
