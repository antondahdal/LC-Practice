package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week09.AverageOfLevels;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageOfLevelsTest {

    private AverageOfLevels sol;

    @BeforeEach
    void setUp() {
        sol = new AverageOfLevels();
    }

    @Test
    void exampleTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        assertEquals(List.of(3.0, 14.5, 11.0), sol.averageOfLevels(root));
    }

    @Test
    void singleNode() {
        assertEquals(List.of(1.0), sol.averageOfLevels(new TreeNode(1)));
    }

    @Test
    void leftOnly() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);

        assertEquals(List.of(1.0, 2.0, 3.0), sol.averageOfLevels(root));
    }

    @Test
    void oneChildOnTheBottom() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);

        assertEquals(List.of(1.0, 2.5, 4.0), sol.averageOfLevels(root));
    }

    @Test
    void valuesTooBigForIntSum() {
        TreeNode root = new TreeNode(2147483647);
        root.left = new TreeNode(2147483647);
        root.right = new TreeNode(2147483647);

        assertEquals(List.of(2147483647.0, 2147483647.0), sol.averageOfLevels(root));
    }
}
