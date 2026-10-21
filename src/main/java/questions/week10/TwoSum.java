package questions.week10;

import java.util.HashMap;

/**
 * 1. Two Sum
 * https://leetcode.com/problems/two-sum/
 *
 * Return the indices of the two numbers that add up to target.
 * Exactly one solution. Do not use the same element twice.
 * Any order is fine.
 *
 * Example: [2,7,11,15], target 9 -> [0,1].
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
       
        HashMap<Integer,Integer> map =new HashMap<>();
        int[] answer=new int[2];
        if (nums.length==0||(nums.length==1 && nums[0]!=target)) return answer;
        for(int i=0;i<nums.length;i++){
           
            if(map.containsKey((target-nums[i]))){
                
                answer[0]=map.get((target-nums[i]));
                answer[1]=i;
                System.out.println("  answer[0] "+map.get((target-nums[i]))+"  answer[1] "+i);
                return answer;
            }
            map.put( nums[i],i);
          
        }
        return answer;
    }
}
