package questions.week08;

/**
 * 70. Climbing Stairs
 * https://leetcode.com/problems/climbing-stairs/
 *
 * You climb a staircase with n steps.
 * Each move is either 1 step or 2 steps.
 * Return how many different ways reach the top.
 *
 * Example: n = 3 -> 3 (1+1+1, 1+2, 2+1).
 */
public class ClimbingStairs {

    public int climbStairs(int n) {
        if(n==1||n==2) return n;
       int[] stairs=new int[n+1];
       stairs[0]=0;
       stairs[1]=1;
       stairs[2]=2;
       for(int i=3;i<=n;i++){
        stairs[i]=stairs[i-1]+stairs[i-2];
       }
        return stairs[n];
    }
}
