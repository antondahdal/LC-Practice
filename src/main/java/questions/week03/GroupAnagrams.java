package questions.week03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 49. Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 *
 * Put words with the same letters together.
 *
 * Example: ["eat","tea","tan","ate","nat","bat"]
 */
public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs==null || strs.length==0) return List.of();
        Map<String,List<String>> map=new HashMap<>();
        for(int i=0;i<strs.length;i++){
            char[] arr=strs[i].toCharArray();
            Arrays.sort(arr);
            String key=String.valueOf(arr);
            if(!map.containsKey(key)){
                map.put(key,new ArrayList<>());
            }
            map.get(key).add(strs[i]);
        }
        return new ArrayList<>(map.values());
    }
}
