package questions.week10;

import java.util.Stack;

/**
 * 20. Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/
 *
 * A string of brackets is valid when every closer matches the opener
 * that is still open, and nothing is left open at the end.
 *
 * Example: "()[]{}" is true. "([)]" is false.
 */
public class ValidParentheses {

    public boolean isValid(String s) {
        Stack<Character> stack=new Stack<>();
        if(s.length()<=1||(s.length()>=1&&(s.charAt(0)=='}'||s.charAt(0)==']'||s.charAt(0)==')'))) return false;
        for(char x:s.toCharArray()){
            if(x=='{'||x=='['||x=='('){
                stack.push(x);
            }
            else if    (stack.isEmpty()&&(x==']'||x=='}'||x==')')) return  false;
       
            else{
                if(!stack.isEmpty()){
                    char tmp=stack.pop();
                    if( (tmp=='{'&&x!='}')||(tmp=='('&&x!=')')||(tmp=='['&&x!=']')) return false;
                }

                }
               
        }
        if(!stack.isEmpty()) return  false;
        return true;
    }
}
