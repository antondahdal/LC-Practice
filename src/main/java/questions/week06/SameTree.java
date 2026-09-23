package questions.week06;

import java.util.LinkedList;
import java.util.Queue;

import questions.common.TreeNode;

/**
 * 100. Same Tree
 * https://leetcode.com/problems/same-tree/
 *
 * Given the roots of two binary trees p and q, write a function to check if
 * they are the same or not. Two binary trees are considered the same if they
 * are structurally identical, and the nodes have the same value.
 *
 * Example: p = 1 / 2 3, q = 1 / 2 3 → true. p = 1 / 2, q = 1 / null 2 → false.
 */
public class SameTree {

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null) return true;
        if((p==null&&q!=null)||(p!=null&&q==null)) return false;
        Queue<TreeNode> queuep = new LinkedList<>();
        Queue<TreeNode> queueq = new LinkedList<>();
        queuep.add(p);
        queueq.add(q);
        while(!queuep.isEmpty()||!queueq.isEmpty()){
            if(queuep.isEmpty()||queueq.isEmpty()) return false;
            TreeNode cuurP=queuep.remove();
            TreeNode currQ=queueq.remove();
            if((cuurP==null && currQ==null )) continue;
            if((cuurP==null && currQ!=null )||(cuurP!=null && currQ==null )
                ||(cuurP.val!=currQ.val))  return false;
              
            
            if((cuurP.right!=null &&currQ.right==null)||(cuurP.right==null &&currQ.right!=null)
            ) return false;
         if((cuurP.right!=null &&currQ.right!=null)&&(cuurP.right.val!=currQ.right.val))return false;

            if((cuurP.left!=null &&currQ.left==null)||(cuurP.left==null &&currQ.left!=null)
               ) return false;
                if( (cuurP.left!=null &&currQ.left!=null)&&(cuurP.left.val!=currQ.left.val))return false;
                
                queuep.add(cuurP.left);
                queuep.add(cuurP.right);
                queueq.add(currQ.left);
                queueq.add(currQ.right);

        }



        return true;
    }
}
