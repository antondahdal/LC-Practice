package questions.week04;

/**
 * 424. Longest Repeating Character Replacement
 * https://leetcode.com/problems/longest-repeating-character-replacement/
 *
 * At most k changes. Longest contiguous stretch that can become one letter.
 *
 * Example: "ABAB" k=2 -> 4
 */
public class CharacterReplacement {

    public int characterReplacement(String s, int k) {
        int[] count=new int[26];
        int left=0;
        int best=0;
        int maxCount=0;
        for(int right=0;right<s.length();right++){
            int idx=s.charAt(right)-'A';
            count[idx]++;
            if(count[idx]>maxCount){
                maxCount=count[idx];
            }
            while((right-left+1)-maxCount>k){
                count[s.charAt(left)-'A']--;
                left++;
            }
            int len=right-left+1;
            if(len>best) best=len;
        }
        return best;
    }
}
