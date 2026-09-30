package questions.week07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 56. Merge Intervals
 * https://leetcode.com/problems/merge-intervals/
 *
 * Given an array of intervals where intervals[i] = [start, end],
 * merge all overlapping intervals and return the ones that are left.
 * The input is not sorted. Touching intervals ([1,4] and [4,5]) count as overlapping.
 *
 * Example: [[1,3],[2,6],[8,10],[15,18]] -> [[1,6],[8,10],[15,18]].
 * [[1,4],[4,5]] -> [[1,5]].
 */
public class MergeIntervals {

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> list = new ArrayList<>();
        for(int i=0;i<intervals.length;i++){
            int[] pair=intervals[i];

            if(list.isEmpty()) list.add(pair);
            else{
                int[] tmpPair=list.get(list.size()-1);
                if(tmpPair[1]>=pair[0]) {
                    int[] newPair= new int[2];
                    newPair[0]=tmpPair[0];
                    newPair[1]=newPair[1] = Math.max(tmpPair[1], pair[1]);
                    list.remove(list.size()-1);
                    list.add(newPair);

                }
                else{
                    list.add(pair);
                }
            }
           
        }
       
        return list.toArray(new int[list.size()][]);
    }
}
