package questions.week06;

import questions.common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 103. Binary Tree Zigzag Level Order Traversal
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 *
 * Given the root of a binary tree, return the zigzag level order traversal of
 * its nodes' values: first row left to right, next row right to left, and so on.
 *
 * Example: 3 / 9 20 / 15 7 → [[3], [20, 9], [15, 7]]
 */
public class ZigzagLevelOrder {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<List<Integer>>();
        Queue<TreeNode> que=new LinkedList<>();
        if(root==null ) return res;
        if((root.left==null&&root.right==null)){
            ArrayList<Integer> tmpList=new ArrayList<>();
            tmpList.add(root.val);
            res.add(tmpList);
            return  res;
        }
        boolean flag=false;
        que.add(root);
        while(!que.isEmpty()){
            int size=que.size();
           
            ArrayList<Integer> tmpList=new ArrayList<>();
            for(int i=0;i<size;i++){
                TreeNode tmp=que.remove();
                tmpList.add(tmp.val);
                if(tmp.left!=null){
                    que.add(tmp.left);
                }
                if(tmp.right!=null){
                    que.add(tmp.right);
                }
            }
            if(flag) {
                Collections.reverse(tmpList);
                res.add(tmpList);
            flag=false;
            }
            else{
                res.add(tmpList);
                flag=true;
            }
            

        }

        return res;
    }
}
