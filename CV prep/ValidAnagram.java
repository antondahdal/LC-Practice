import java.security.KeyStore.Entry;
import java.util.HashMap;

/**
 * 242. Valid Anagram
 * https://leetcode.com/problems/valid-anagram/
 *
 * Return true if t is an anagram of s.
 */
public class ValidAnagram {

    public boolean isAnagram(String s, String t) {
        if(s.length()!=t.length()) return false;
        HashMap<Character,Integer> map=new HashMap<>();

        for(char x:s.toCharArray()){
            if(map.containsKey(x)){
                int val=map.get(x);
                map.put(x, ++val);
            }
            if(!map.containsKey(x)){
                map.put(x, 1);
            }
        }

        for(char x:t.toCharArray()){
            if(!map.containsKey(x)){
                return false;
            }
            if(map.containsKey(x)){
                int val=map.get(x);
                if(val==0) return false;
                map.put(x, --val);
            }
        }

        for(Integer x: map.values() ){
            if(x>0) return false;
        }
        return true;

    }
}
