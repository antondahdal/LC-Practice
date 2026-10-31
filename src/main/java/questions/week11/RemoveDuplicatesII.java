package questions.week11;

import java.util.ArrayList;
import java.util.List;

/**
 * 80. Remove Duplicates from Sorted Array II
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 *
 * nums is sorted. Each value may stay at most twice.
 * Change nums in place. Return how many values you kept.
 * The tail after that count does not matter.
 *
 * Example: [1,1,1,2,2,3] -> length 5, first five are [1,1,2,2,3].
 */
public class RemoveDuplicatesII {
public static void main(String[] args) {
    ArrayList arr=new ArrayList<>();
    arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(6);

    System.out.println(foo(arr));
}
   
    public static int foo(List<Integer> nums) {
    int x = -1;
    int y = -1;
    for (Integer num : nums) {
        if (x < num) {
          
            y = x;
            x = num;
          
        }
        if(x>num &&num>y){
            y=num;
        }
    }
    return y;
}
}
