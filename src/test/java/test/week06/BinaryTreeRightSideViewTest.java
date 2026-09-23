package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.BinaryTreeRightSideView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryTreeRightSideViewTest {

    private BinaryTreeRightSideView sol;

    @BeforeEach
    void setUp() {
        sol = new BinaryTreeRightSideView();
    }

    @Test
    void exampleTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        assertEquals(List.of(1, 3, 4), sol.rightSideView(root));
    }

    @Test
    void emptyTree() {
        assertTrue(sol.rightSideView(null).isEmpty());
    }

    @Test
    void singleNode() {
        assertEquals(List.of(1), sol.rightSideView(new TreeNode(1)));
    }

    @Test
    void leftSpineOnly() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        assertEquals(List.of(1, 2, 3), sol.rightSideView(root));
    }

    @Test
    void deeperLeftIsVisibleWhenRightIsShort() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        assertEquals(List.of(1, 3, 4), sol.rightSideView(root));
    }
}
