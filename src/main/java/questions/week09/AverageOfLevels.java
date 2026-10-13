package questions.week09;

import questions.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 637. Average of Levels in Binary Tree
 * https://leetcode.com/problems/average-of-levels-in-binary-tree/
 *
 * Given the root of a binary tree, return the average of the node values
 * on each level.
 *
 * Example: 3 / 9 20 / 15 7 → [3, 14.5, 11]
 */
public class AverageOfLevels {

    public List<Double> averageOfLevels(TreeNode root) {
        if(root==null){
            return null;
        }
        ArrayList<Double> ret=new ArrayList<>();
        if(root.left==null&&root.right==null){
        ret.add(root.val+0.0);  
        return ret;
        }


      
        Queue<TreeNode> queue= new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()){
            double avg=0;
            int size = queue.size();  
            for(int i=0;i<size;i++){
                TreeNode tmp=queue.remove();
                avg+=tmp.val;
                if (tmp.left != null) queue.add(tmp.left);
                if (tmp.right != null) queue.add(tmp.right);
            }
            ret.add(avg/size);
            
          
        }

        return ret;
    }
}
