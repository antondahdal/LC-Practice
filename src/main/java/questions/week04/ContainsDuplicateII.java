package questions.week04;

import java.util.HashMap;
import java.util.Map;

/**
 * 219. Contains Duplicate II
 * https://leetcode.com/problems/contains-duplicate-ii/
 *
 * Same value at i and j with |i-j| <= k.
 *
 * Example: [1,2,3,1], k=3 -> true
 */
public class ContainsDuplicateII {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                int last=map.get(nums[i]);
                if(i-last<=k) return true;
            }
            map.put(nums[i],i);
        }
        return false;
    }
}
