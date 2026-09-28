package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.CountCompleteTreeNodes;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountCompleteTreeNodesTest {

    private CountCompleteTreeNodes sol;

    @BeforeEach
    void setUp() {
        sol = new CountCompleteTreeNodes();
    }

    @Test
    void exampleSix() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);

        assertEquals(6, sol.countNodes(root));
    }

    @Test
    void emptyTree() {
        assertEquals(0, sol.countNodes(null));
    }

    @Test
    void singleNode() {
        assertEquals(1, sol.countNodes(new TreeNode(1)));
    }

    @Test
    void perfectSeven() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        assertEquals(7, sol.countNodes(root));
    }

    @Test
    void lastLevelOneNode() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);

        assertEquals(4, sol.countNodes(root));
    }
}
