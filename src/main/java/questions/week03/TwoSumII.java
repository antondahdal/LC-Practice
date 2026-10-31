package questions.week03;

import java.util.HashMap;

/**
 * 167. Two Sum II - Input Array Is Sorted
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * Sorted array. Find two different indexes that sum to target.
 * Return 1-based indexes, smaller first. O(1) extra space.
 *
 * Example: [2,7,11,15], target 9 -> [1,2]
 */
public class TwoSumII {

    public int[] twoSum(int[] numbers, int target) {
       
        int[] retInt=new int[2];
        if(numbers.length<=1)  return retInt;
        HashMap<Integer,Integer> map= new HashMap<>();
        for(int i=0;i<=numbers.length-1;i++){

            if( map.containsKey(target-numbers[i])){
                retInt[0]=i;
                retInt[1]=map.get(target-numbers[i]) ;
                return retInt;
            }
            else{
                map.put(target-numbers[i], i);
            }
        }

        return retInt;
      
    }
}
