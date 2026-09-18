package questions.week04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 380. Insert Delete GetRandom O(1)
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 *
 * Insert / remove / getRandom all average O(1). No duplicates.
 */
public class RandomizedSet {

    private List<Integer> list=new ArrayList<>();
    private Map<Integer,Integer> map=new HashMap<>();
    private Random rand=new Random();

    public RandomizedSet() {
    }

    public boolean insert(int val) {
        if(map.containsKey(val)) return false;
        map.put(val,list.size());
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        int idx=map.get(val);
        int last=list.get(list.size()-1);
        list.set(idx,last);
        map.put(last,idx);
        list.remove(list.size()-1);
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
