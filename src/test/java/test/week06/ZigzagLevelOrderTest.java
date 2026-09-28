package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.ZigzagLevelOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZigzagLevelOrderTest {

    private ZigzagLevelOrder sol;

    @BeforeEach
    void setUp() {
        sol = new ZigzagLevelOrder();
    }

    @Test
    void exampleTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        List<List<Integer>> expected = List.of(
                List.of(3),
                List.of(20, 9),
                List.of(15, 7)
        );
        assertEquals(expected, sol.zigzagLevelOrder(root));
    }

    @Test
    void emptyTree() {
        assertTrue(sol.zigzagLevelOrder(null).isEmpty());
    }

    @Test
    void singleNode() {
        assertEquals(List.of(List.of(1)), sol.zigzagLevelOrder(new TreeNode(1)));
    }

    @Test
    void fullFourLevels() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.right.right.right = new TreeNode(9);

        List<List<Integer>> expected = List.of(
                List.of(1),
                List.of(3, 2),
                List.of(4, 5, 6, 7),
                List.of(9, 8)
        );
        assertEquals(expected, sol.zigzagLevelOrder(root));
    }

    @Test
    void lopsidedTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        List<List<Integer>> expected = List.of(
                List.of(1),
                List.of(3, 2),
                List.of(4, 5)
        );
        assertEquals(expected, sol.zigzagLevelOrder(root));
    }
}
