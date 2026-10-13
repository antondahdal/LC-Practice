package test.week09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.common.TreeNode;
import questions.week09.ConvertSortedArrayToBST;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertSortedArrayToBSTTest {

    private ConvertSortedArrayToBST sol;

    @BeforeEach
    void setUp() {
        sol = new ConvertSortedArrayToBST();
    }

    @Test
    void example() {
        assertBalancedBst(new int[] {-10, -3, 0, 5, 9}, sol.sortedArrayToBST(new int[] {-10, -3, 0, 5, 9}));
    }

    @Test
    void singleNode() {
        TreeNode root = sol.sortedArrayToBST(new int[] {1});
        assertEquals(1, root.val);
        assertTrue(root.left == null && root.right == null);
    }

    @Test
    void twoValues() {
        assertBalancedBst(new int[] {1, 3}, sol.sortedArrayToBST(new int[] {1, 3}));
    }

    @Test
    void alreadyTheMiddle() {
        assertBalancedBst(new int[] {1, 2, 3}, sol.sortedArrayToBST(new int[] {1, 2, 3}));
    }

    private static void assertBalancedBst(int[] nums, TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        collect(root, inorder);
        assertEquals(nums.length, inorder.size());
        for (int i = 0; i < nums.length; i++) {
            assertEquals(nums[i], inorder.get(i));
        }
        assertTrue(height(root) >= 0);
    }

    private static void collect(TreeNode node, List<Integer> inorder) {
        if (node == null) {
            return;
        }
        collect(node.left, inorder);
        inorder.add(node.val);
        collect(node.right, inorder);
    }

    private static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        if (left < 0 || right < 0 || Math.abs(left - right) > 1) {
            return -1;
        }
        return 1 + Math.max(left, right);
    }
}
