package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.LevelOrderTraversal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelOrderTraversalTest {

    private LevelOrderTraversal sol;

    @BeforeEach
    void setUp() {
        sol = new LevelOrderTraversal();
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
                List.of(9, 20),
                List.of(15, 7)
        );
        assertEquals(expected, sol.levelOrder(root));
    }

    @Test
    void emptyTree() {
        assertTrue(sol.levelOrder(null).isEmpty());
    }

    @Test
    void singleNode() {
        assertEquals(List.of(List.of(1)), sol.levelOrder(new TreeNode(1)));
    }

    @Test
    void leftOnly() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);

        List<List<Integer>> expected = List.of(
                List.of(1),
                List.of(2),
                List.of(3)
        );
        assertEquals(expected, sol.levelOrder(root));
    }

    @Test
    void bothParentsHaveKids() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        List<List<Integer>> expected = List.of(
                List.of(1),
                List.of(2, 3),
                List.of(4, 5, 6, 7)
        );
        assertEquals(expected, sol.levelOrder(root));
    }
}
