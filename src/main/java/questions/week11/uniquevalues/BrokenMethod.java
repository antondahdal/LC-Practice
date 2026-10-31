package questions.week11.uniquevalues;

import java.util.Arrays;
import java.util.List;

public class BrokenMethod {

    public static int foo(List<Integer> nums) {
        int x = -1;
        int y = -1;
        for (Integer num : nums) {
            if (x < num) {
                y = x;
                x = num;
            }
        }
        return y;
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 3, 4);
        System.out.println(foo(nums));
    }
}
