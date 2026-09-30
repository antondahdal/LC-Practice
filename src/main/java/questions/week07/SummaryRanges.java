package questions.week07;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 228. Summary Ranges
 * https://leetcode.com/problems/summary-ranges/
 *
 * Given a sorted array of unique ints, return the smallest sorted list of ranges
 * that covers every number exactly once.
 * A range [a, b] is written "a->b" if a != b, and "a" if a == b.
 *
 * Example: [0,1,2,4,5,7] -> ["0->2","4->5","7"].
 * [0,2,3,4,6,8,9] -> ["0","2->4","6","8->9"].
 */
public class SummaryRanges {

    public List<String> summaryRanges(int[] nums) {
        if(nums.length==0) return new ArrayList<>();
        if(nums.length==1) return new ArrayList<>(List.of(String.valueOf(nums[0])));
        ArrayList<String> ret=new ArrayList<>();
      Stack<Integer> tmpStack=new Stack<>();
        for(int i=0;i<nums.length-1;i++){
          if(nums[i]+1!=nums[i+1]&&tmpStack.isEmpty()) ret.add(String.valueOf(nums[i]));
          if(nums[i]+1!=nums[i+1]&&!tmpStack.isEmpty()) {
            tmpStack.push(nums[i]);
            ret.add(tmpStack.firstElement()+"->"+tmpStack.lastElement());
            tmpStack=new Stack<>();
        }
           if (nums[i]+1==nums[i+1]){
            tmpStack.push(nums[i]);

          }

        }
      
        if(!tmpStack.isEmpty()){
            tmpStack.push(nums[nums.length-1]);
            ret.add(tmpStack.firstElement()+"->"+tmpStack.lastElement());
        }
        else{
            ret.add(String.valueOf(nums[nums.length-1]));
        }
        
        return ret;
    }
}
