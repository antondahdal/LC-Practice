package questions.week07;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 452. Minimum Number of Arrows to Burst Balloons
 * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
 *
 * Each balloon is points[i] = [xStart, xEnd] on the x-axis.
 * An arrow shot straight up at x bursts every balloon with xStart <= x <= xEnd.
 * Return the fewest arrows that burst all balloons.
 *
 * Example: [[10,16],[2,8],[1,6],[7,12]] -> 2.
 * One arrow at x = 6 bursts [2,8] and [1,6].
 * One arrow at x = 11 bursts [10,16] and [7,12].
 */
public class MinArrowsBurstBalloons {

    public int findMinArrowShots(int[][] points) {
        if(points.length==0) return 0;
        if(points.length==1) return 1;

        ArrayList<int[]> ret=new ArrayList<>();
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        for(int[] x:points){
            System.out.println("X is "+x[0]+"---"+x[1]);
            if(ret.isEmpty()) ret.add(x);
          
            else{
                int[] tmp=ret.get(ret.size()-1);
                System.out.println("TMP IS "+tmp[0]+"---"+tmp[1]);
                if(x[0]<=tmp[1]){
                    // tmp's arrow already pops x, keep nothing
                }
                else{
                    ret.add(x);
                }
            }
          
        }
        return ret.size();
    }
}
