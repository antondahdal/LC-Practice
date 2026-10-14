package questions.week09;

/**
 * 392. Is Subsequence
 * https://leetcode.com/problems/is-subsequence/
 *
 * s and t are strings.
 * Return true if s is a subsequence of t.
 * Characters of s must appear in t in the same order, not necessarily next to each other.
 *
 * Example: s = "abc", t = "ahbgdc" → true.
 * s = "axc", t = "ahbgdc" → false.
 */
public class IsSubsequence {

    public boolean isSubsequence(String s, String t) {
        if(s.length()==0) return true;
        if(t.length()==0) return false;
        if(s.length()==0&&t.length()==0) return true;
       if(s.length()==1&&t.length()==1 &&(s.charAt(0)!=t.charAt(0))) return false;
        int sInd=0;
        int tInd=0;
        while(tInd<t.length()){
         
            if(s.charAt(sInd)==t.charAt(tInd)){
                sInd++;
                tInd++;
            }
            else{
                tInd++;
            }
            if(sInd ==s.length()) return true;

        }
        return false;
    }
}
