package questions.week06;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import questions.common.TreeNode;

/**
 * 222. Count Complete Tree Nodes
 * https://leetcode.com/problems/count-complete-tree-nodes/
 *
 * Given the root of a complete binary tree, return the number of nodes.
 * Complete: every level is full except possibly the last, and the last level
 * is filled from the left.
 *
 * Example: 1 / 2 3 / 4 5 6 → 6
 */
public class CountCompleteTreeNodes {

    public int countNodes(TreeNode root) {
        if (root==null) return 0;
       Queue<TreeNode> que=new LinkedList<>();
       ArrayList<Integer> x=new ArrayList<>();
       que.add(root);
       while (!que.isEmpty()){
        TreeNode tmp=que.remove();
        x.add(tmp.val);
        if(tmp.left!=null){
            que.add(tmp.left);
        }
        if(tmp.right!=null){
            que.add(tmp.right);
        }
       }
        
        
        return x.size();
    }
}
