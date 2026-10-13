package questions.week09;

import java.util.ArrayList;
import java.util.List;

/**
 * 22. Generate Parentheses
 * https://leetcode.com/problems/generate-parentheses/
 *
 * n is how many pairs.
 * Return every string that uses exactly n pairs and is valid.
 * Order does not matter.
 *
 * Example: n = 2 → (()) and ()().
 */
public class GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> answers = new ArrayList<>();
        build(answers, new StringBuilder(), 0, 0, n);
        return answers;
    }

    private void build(List<String> answers, StringBuilder partial, int opens, int closes, int n) {
        if (partial.length() == 2 * n) {
            answers.add(partial.toString());
            return;
        }
        if (opens < n) {
            partial.append('(');
            build(answers, partial, opens + 1, closes, n);
            partial.deleteCharAt(partial.length() - 1);
        }
        if (closes < opens) {
            partial.append(')');
            build(answers, partial, opens, closes + 1, n);
            partial.deleteCharAt(partial.length() - 1);
        }
    }
}
