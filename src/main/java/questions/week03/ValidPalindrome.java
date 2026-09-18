package questions.week03;

/**
 * 125. Valid Palindrome
 * https://leetcode.com/problems/valid-palindrome/
 *
 * Ignore case and skip non letter/digit. Does it read the same both ways?
 *
 * Example: "A man, a plan, a canal: Panama" -> true
 */
public class ValidPalindrome {

    public boolean isPalindrome(String s) {
        int left=0;
        int right=s.length()-1;
        while(left<right){
            if(!Character.isLetterOrDigit(s.charAt(left))){
                left++;
            }
            else if(!Character.isLetterOrDigit(s.charAt(right))){
                right--;
            }
            else if(Character.toLowerCase(s.charAt(left))!=Character.toLowerCase(s.charAt(right))){
                return false;
            }
            else{
                left++;
                right--;
            }
        }
        return true;
    }
}
