package test.week06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week06.LowestCommonAncestor;

import static org.junit.jupiter.api.Assertions.assertSame;

class LowestCommonAncestorTest {

    private LowestCommonAncestor sol;

    private TreeNode n3, n5, n1, n6, n2, n0, n8, n7, n4;

    @BeforeEach
    void setUp() {
        sol = new LowestCommonAncestor();
        n3 = new TreeNode(3);
        n5 = new TreeNode(5);
        n1 = new TreeNode(1);
        n6 = new TreeNode(6);
        n2 = new TreeNode(2);
        n0 = new TreeNode(0);
        n8 = new TreeNode(8);
        n7 = new TreeNode(7);
        n4 = new TreeNode(4);
        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;
    }

    @Test
    void splitAtRoot() {
        assertSame(n3, sol.lowestCommonAncestor(n3, n5, n1));
    }

    @Test
    void oneIsAncestorOfOther() {
        assertSame(n5, sol.lowestCommonAncestor(n3, n5, n4));
    }

    @Test
    void deepSplit() {
        assertSame(n2, sol.lowestCommonAncestor(n3, n7, n4));
    }

    @Test
    void splitInLeftSubtree() {
        assertSame(n5, sol.lowestCommonAncestor(n3, n6, n4));
    }

    @Test
    void acrossRootFromDeep() {
        assertSame(n3, sol.lowestCommonAncestor(n3, n7, n8));
    }

    @Test
    void twoNodeTree() {
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        a.left = b;
        assertSame(a, sol.lowestCommonAncestor(a, a, b));
    }
}
