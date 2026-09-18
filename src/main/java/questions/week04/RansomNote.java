package questions.week04;

import java.util.HashMap;
import java.util.Map;

/**
 * 383. Ransom Note
 * https://leetcode.com/problems/ransom-note/
 *
 * Build ransomNote from magazine. Each letter at most once.
 * Extra letters in magazine are OK.
 *
 * Example: "aa","aab" -> true. "aa","ab" -> false.
 */
public class RansomNote {

    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character,Integer> map=new HashMap<>();
        for(int i=0;i<magazine.length();i++){
            char c=magazine.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
        }
        for(int i=0;i<ransomNote.length();i++){
            char c=ransomNote.charAt(i);
            if(!map.containsKey(c) || map.get(c)==0) return false;
            map.put(c,map.get(c)-1);
        }
        return true;
    }
}
