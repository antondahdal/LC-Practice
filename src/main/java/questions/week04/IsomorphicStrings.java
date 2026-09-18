package questions.week04;

import java.util.HashMap;
import java.util.Map;

/**
 * 205. Isomorphic Strings
 * https://leetcode.com/problems/isomorphic-strings/
 *
 * Each letter in s maps to one letter in t, and no two share a partner.
 *
 * Example: "egg","add" -> true. "foo","bar" -> false.
 */
public class IsomorphicStrings {

    public boolean isIsomorphic(String s, String t) {
        if(s.length()!=t.length()) return false;
        Map<Character,Character> map=new HashMap<>();
        for(int i=0;i<s.length();i++){
            char a=s.charAt(i);
            char b=t.charAt(i);
            if(!map.containsKey(a) && !map.containsValue(b)){
                map.put(a,b);
            }
            else if(map.containsKey(a) && map.get(a)!=b){
                return false;
            }
            else if(!map.containsKey(a) && map.containsValue(b)){
                return false;
            }
        }
        return true;
    }
}
