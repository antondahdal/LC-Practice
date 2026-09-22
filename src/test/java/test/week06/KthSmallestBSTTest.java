package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.KthSmallestBST;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthSmallestBSTTest {

    private KthSmallestBST sol;

    @BeforeEach
    void setUp() {
        sol = new KthSmallestBST();
    }

    @Test
    void exampleK1() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);
        assertEquals(1, sol.kthSmallest(root, 1));
    }

    @Test
    void exampleK3() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(1);
        assertEquals(3, sol.kthSmallest(root, 3));
    }

    @Test
    void singleNode() {
        assertEquals(7, sol.kthSmallest(new TreeNode(7), 1));
    }

    @Test
    void lastIsLargest() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        assertEquals(3, sol.kthSmallest(root, 3));
    }

    @Test
    void leftChainK2() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        assertEquals(2, sol.kthSmallest(root, 2));
    }
}
