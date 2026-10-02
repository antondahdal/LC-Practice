package questions.week07;

import java.util.ArrayList;

/**
 * 57. Insert Interval
 * https://leetcode.com/problems/insert-interval/
 *
 * intervals[i] = [start, end], sorted by start, and no two overlap.
 * Insert newInterval so the result is still sorted with no overlaps
 * (merge where needed). Return the result.
 *
 * Example: intervals [[1,3],[6,9]], newInterval [2,5] -> [[1,5],[6,9]].
 * intervals [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval [4,8]
 * -> [[1,2],[3,10],[12,16]].
 */
public class InsertInterval {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList <int[]> ret=new ArrayList<>();
        boolean placed=false;

        for(int[] x:intervals){
            if(x[1]<newInterval[0]){
                ret.add(x);
            }
            else if(x[0]>newInterval[1]){
                if(!placed){
                    ret.add(newInterval);
                    placed=true;
                }
                ret.add(x);
            }
            else{
                int [] tmp=new int[2];
                tmp[0]=Math.min(x[0],newInterval[0]);
                tmp[1]=Math.max(x[1],newInterval[1]);
                newInterval=tmp;
            }
        }
        if(!placed) ret.add(newInterval);

        int[][] retarr=new int[ret.size()][];
        for(int i=0;i<ret.size();i++){
            retarr[i]=ret.get(i);
        }
        return retarr;
    }
}
