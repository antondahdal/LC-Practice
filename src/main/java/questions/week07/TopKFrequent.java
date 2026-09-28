package questions.week07;

import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 347. Top K Frequent Elements
 * https://leetcode.com/problems/top-k-frequent-elements/
 *
 * Given an int array nums and an int k, return the k values that appear most often.
 * Any order is fine. The answer is guaranteed to be unique.
 *
 * Example: [1,1,1,2,2,3], k = 2 -> [1,2]. [1], k = 1 -> [1].
 */
public class TopKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        int[] ret=new int[k];
      
        HashMap<Integer,Integer> map=new HashMap<>();
        PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
        for(int x:nums){
            if(map.containsKey(x)){
                int tmpval=map.get(x);
                tmpval++;
                map.put(x, tmpval);
            }
            if(!map.containsKey(x)) map.put(x, 1);
            
            

        }
      max.addAll(map.keySet());
        System.out.println(max);
        for(int i=0;i<k;i++){
            ret[i]=max.poll();
        }

        return ret;
    }
}
