package questions.week08;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 207. Course Schedule
 * https://leetcode.com/problems/course-schedule/
 *
 * There are numCourses courses, labeled 0 to numCourses - 1.
 * prerequisites[i] = [a, b] means you must take b before a.
 * Return true if you can finish every course.
 *
 * Example: numCourses = 2, [[1,0]] -> true (take 0, then 1).
 * Example: numCourses = 2, [[1,0],[0,1]] -> false (each waits on the other).
 */
public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> unlocks = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            unlocks.add(new ArrayList<>());
        }
        int[] waitingOn = new int[numCourses];

        for (int[] pair : prerequisites) {
            int course = pair[0];
            int before = pair[1];
            unlocks.get(before).add(course);
            waitingOn[course]++;
        }

        Queue<Integer> ready = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (waitingOn[i] == 0) ready.add(i);
        }

        int taken = 0;
        while (!ready.isEmpty()) {
            int current = ready.poll();
            taken++;
            for (int next : unlocks.get(current)) {
                waitingOn[next]--;
                if (waitingOn[next] == 0) ready.add(next);
            }
        }

        return taken == numCourses;
    }
}
