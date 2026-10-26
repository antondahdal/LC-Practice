package questions.week11;

import java.util.HashSet;

/**
 * 217. Contains Duplicate
 * https://leetcode.com/problems/contains-duplicate/
 *
 * Return true if any value appears at least twice.
 * Return false if every value is distinct.
 *
 * Example: [1,2,3,1] -> true. [1,2,3,4] -> false.
 */
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        if(nums.length<=1) return false;
        HashSet<Integer> set=new HashSet<>();
        for(int x:nums){
            if(set.contains(x)) return true;
            else set.add(x);
        }

        return false;
    }
}
