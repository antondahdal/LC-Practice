package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.MaxDepthBinaryTree;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDepthBinaryTreeTest {

    private MaxDepthBinaryTree sol;

    @BeforeEach
    void setUp() {
        sol = new MaxDepthBinaryTree();
    }

    @Test
    void exampleTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        assertEquals(3, sol.maxDepth(root));
    }

    @Test
    void emptyTree() {
        assertEquals(0, sol.maxDepth(null));
    }

    @Test
    void singleNode() {
        assertEquals(1, sol.maxDepth(new TreeNode(1)));
    }

    @Test
    void leftChain() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        assertEquals(3, sol.maxDepth(root));
    }
}
