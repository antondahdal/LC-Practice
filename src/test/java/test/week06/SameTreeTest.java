package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.SameTree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SameTreeTest {

    private SameTree sol;

    @BeforeEach
    void setUp() {
        sol = new SameTree();
    }

    @Test
    void exampleSame() {
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(3);
        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(2);
        q.right = new TreeNode(3);
        assertTrue(sol.isSameTree(p, q));
    }

    @Test
    void exampleDifferentStructure() {
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        TreeNode q = new TreeNode(1);
        q.right = new TreeNode(2);
        assertFalse(sol.isSameTree(p, q));
    }

    @Test
    void exampleDifferentValues() {
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(1);
        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(1);
        q.right = new TreeNode(2);
        assertFalse(sol.isSameTree(p, q));
    }

    @Test
    void bothEmpty() {
        assertTrue(sol.isSameTree(null, null));
    }

    @Test
    void oneEmpty() {
        assertFalse(sol.isSameTree(new TreeNode(1), null));
    }
}
