package questions.week04;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * Unsorted nums. Longest run of consecutive values. Length only.
 *
 * Example: [100,4,200,1,3,2] -> 4
 */
public class LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {
        if(nums.length==0) return 0;
        Set<Integer> set=new HashSet<>();
        for(int x:nums){
            set.add(x);
        }
        int best=0;
        for(int x:set){
            if(!set.contains(x-1)){
                int cur=x;
                int len=1;
                while(set.contains(cur+1)){
                    cur++;
                    len++;
                }
                if(len>best) best=len;
            }
        }
        return best;
    }
}
