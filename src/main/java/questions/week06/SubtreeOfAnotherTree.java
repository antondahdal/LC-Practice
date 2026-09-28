package questions.week06;

import java.util.LinkedList;
import java.util.Queue;

import questions.common.TreeNode;

/**
 * 572. Subtree of Another Tree
 * https://leetcode.com/problems/subtree-of-another-tree/
 *
 * Given the roots of two binary trees root and subRoot, return true if there is
 * a node in root whose subtree has the same structure and values as subRoot.
 * The subtree of a node is that node plus all of its descendants.
 *
 * Example: root = 3 / 4 5 / 1 2, subRoot = 4 / 1 2 → true
 */
public class SubtreeOfAnotherTree {

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot== null && root==null) return true;
        if (subRoot== null || root==null) return false;
        boolean flag=false;
       
        Queue<TreeNode> que=new LinkedList<>();
        que.add(root);
        while (!que.isEmpty()){
            TreeNode tmp=que.remove();
            if(tmp.val==subRoot.val){
                flag=   pre(tmp, subRoot);
                if(flag) return flag;
            }
            if(tmp.left!=null){
                que.add(tmp.left);
            }
            if(tmp.right!=null){
                que.add(tmp.right);
            }
         
        }


        return flag;
    }
    boolean pre(TreeNode node,TreeNode subRoot) {
        if (node == null &&subRoot==null) return true;
        if ((node == null ||subRoot==null)) return false;
        if(node.val!=subRoot.val) return false;      // here
        
        
    return pre(node.left, subRoot.left)&& pre(node.right,subRoot.right);
    }
}
