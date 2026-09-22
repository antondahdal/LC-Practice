package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.ValidateBST;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateBSTTest {

    private ValidateBST sol;

    @BeforeEach
    void setUp() {
        sol = new ValidateBST();
    }

    @Test
    void exampleValid() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        assertTrue(sol.isValidBST(root));
    }

    @Test
    void exampleInvalid() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);
        assertFalse(sol.isValidBST(root));
    }

    @Test
    void emptyTree() {
        assertTrue(sol.isValidBST(null));
    }

    @Test
    void singleNode() {
        assertTrue(sol.isValidBST(new TreeNode(1)));
    }

    @Test
    void rightChildBreaksAncestor() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(7);
        assertFalse(sol.isValidBST(root));
    }

    @Test
    void equalValuesAreInvalid() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(2);
        assertFalse(sol.isValidBST(root));
    }

    @Test
    void minValueLeafIsValid() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(Integer.MIN_VALUE);
        assertTrue(sol.isValidBST(root));
    }
}
