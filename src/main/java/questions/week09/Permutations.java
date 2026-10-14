package questions.week09;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. Permutations
 * https://leetcode.com/problems/permutations/
 *
 * nums holds distinct integers.
 * Return every ordering of those numbers.
 * Order of the lists does not matter.
 *
 * Example: [1, 2, 3]
 * 1 2 3
 * 1 3 2
 * 2 1 3
 * 2 3 1
 * 3 1 2
 * 3 2 1
 */
public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> answers = new ArrayList<>();
        build(answers, new ArrayList<>(), new boolean[nums.length], nums);
        return answers;
    }

    private void build(List<List<Integer>> answers, List<Integer> partial, boolean[] used, int[] nums) {
        if (partial.size() == nums.length) {
            answers.add(new ArrayList<>(partial));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            partial.add(nums[i]);
            build(answers, partial, used, nums);
            partial.remove(partial.size() - 1);
            used[i] = false;
        }
    }
}
