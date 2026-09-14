package questions.week04; 

import java.util.HashMap;

/**
 * 290. Word Pattern
 * https://leetcode.com/problems/word-pattern/
 *
 * Given a pattern and a string s, return true if s follows the same pattern.
 * Follow means a full match: each letter in pattern maps to one word in s,
 * and each word maps back to one letter.
 *
 * Example: pattern = "abba", s = "dog cat cat dog" -> true
 * Example: pattern = "abba", s = "dog cat cat fish" -> false
 */
public class WordPattern {

    public boolean wordPattern(String pattern, String s) {
        
       String[] arr=s.split(" ");
       if(pattern.length()!=arr.length) return false;
       HashMap<Character,String> map= new HashMap<>();
       for(int i=0;i<pattern.length();i++){

        if(!map.containsKey(pattern.charAt(i))&&!map.containsValue(arr[i])){
            map.put(pattern.charAt(i), arr[i]);
     
          }
       else if(map.containsKey(pattern.charAt(i))&&!map.get(pattern.charAt(i)).equals(arr[i])){
     
            return false;
        }
       else if(!map.containsKey(pattern.charAt(i))&&map.containsValue(arr[i])){

            return false;
         }
        

       
       }
        return true;
    }
}
