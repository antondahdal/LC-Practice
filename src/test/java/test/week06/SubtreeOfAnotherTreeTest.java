package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.SubtreeOfAnotherTree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubtreeOfAnotherTreeTest {

    private SubtreeOfAnotherTree sol;

    @BeforeEach
    void setUp() {
        sol = new SubtreeOfAnotherTree();
    }

    private TreeNode fourOneTwo() {
        TreeNode sub = new TreeNode(4);
        sub.left = new TreeNode(1);
        sub.right = new TreeNode(2);
        return sub;
    }

    @Test
    void exampleTrue() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);

        assertTrue(sol.isSubtree(root, fourOneTwo()));
    }

    @Test
    void extraNodeBelowIsFalse() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(0);

        assertFalse(sol.isSubtree(root, fourOneTwo()));
    }

    @Test
    void sameTreeIsSubtree() {
        assertTrue(sol.isSubtree(fourOneTwo(), fourOneTwo()));
    }

    @Test
    void matchDeepOnRightSide() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.left.left = new TreeNode(1);
        root.right.left.right = new TreeNode(2);

        assertTrue(sol.isSubtree(root, fourOneTwo()));
    }

    @Test
    void sameValuesDifferentShapeIsFalse() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(1);
        root.left.left = new TreeNode(2);

        assertFalse(sol.isSubtree(root, fourOneTwo()));
    }

    @Test
    void singleNodeMatchesLeaf() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);

        assertTrue(sol.isSubtree(root, new TreeNode(2)));
    }
}
