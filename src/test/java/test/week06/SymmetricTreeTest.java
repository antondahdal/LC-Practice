package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.SymmetricTree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SymmetricTreeTest {

    private SymmetricTree sol;

    @BeforeEach
    void setUp() {
        sol = new SymmetricTree();
    }

    @Test
    void exampleSymmetric() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        assertTrue(sol.isSymmetric(root));
    }

    @Test
    void exampleNotSymmetric() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));
        assertFalse(sol.isSymmetric(root));
    }

    @Test
    void singleNode() {
        assertTrue(sol.isSymmetric(new TreeNode(1)));
    }

    @Test
    void emptyTree() {
        assertTrue(sol.isSymmetric(null));
    }

    @Test
    void sameShapeDifferentValues() {
        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        assertFalse(sol.isSymmetric(root));
    }

    @Test
    void identicalSidesAreNotMirrored() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(3), new TreeNode(4)));
        assertFalse(sol.isSymmetric(root));
    }
}
