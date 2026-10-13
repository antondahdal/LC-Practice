package questions.week09;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 17. Letter Combinations of a Phone Number
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 *
 * digits is a string of keys from 2 to 9.
 * Return every letter string those keys can spell.
 * Order does not matter.
 * An empty digits string returns an empty list.
 *
 * Example: "23" -> ad ae af bd be bf cd ce cf.
 */
public class LetterCombinations {

   String[] KEYS = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        ArrayList<String> ret=new ArrayList<>();
        HashSet<Integer> x=new HashSet<>();

        if(digits.length()==1){
            int button=Integer.valueOf(String.valueOf(digits.charAt(0)) );
            for(int i=0;i<KEYS[button].length();i++){
                ret.add(String.valueOf(KEYS[button].charAt(i)) );
            }
        }
        for(int i=0;i<digits.length()-1;i++){
           int button=Integer.valueOf(String.valueOf(digits.charAt(i)) );
            
            if(!x.contains(button+digits.charAt(i+1)) ){
               
                
                System.out.println(button);
               
                int next=Integer.valueOf(String.valueOf(digits.charAt(i+1)) );
            ret.addAll(DoCombine(KEYS[button],KEYS[next]));
            }
            x.add(button);
           
        }
        System.out.println(ret.size());
        return ret;
    }


    private List<String> DoCombine(String x1,String x2){
        ArrayList<String>  ret=new ArrayList<>();
        for(int i=0;i<x1.length();i++){
            for(int j=0;j<x2.length();j++){
                ret.add(x1.charAt(i)+""+x2.charAt(j));
                System.out.println(x1.charAt(i)+""+x2.charAt(j));
            }
          
          
        }
        return ret;
    }
}
