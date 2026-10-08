package questions.week08;

/**
 * 322. Coin Change
 * https://leetcode.com/problems/coin-change/
 *
 * coins[i] is a coin value. You may use each coin any number of times.
 * Return the fewest coins that add up to amount.
 * If you cannot make amount, return -1.
 *
 * Example: coins = [1, 2, 5], amount = 11 -> 3 (5 + 5 + 1).
 */
public class CoinChange {

    public int coinChange(int[] coins, int amount) {
        int[] retarr=new int[amount+1];
        for(int a=1;a<=amount;a++){
            retarr[a]=amount+1;
            for(int i=0;i<coins.length;i++){
                int coin=coins[i];
                if(coin<=a && retarr[a-coin]<=amount){
                    retarr[a]=Math.min(retarr[a], retarr[a-coin]+1);
                }
            }
        }
        if(retarr[amount]>amount) return -1;
        return retarr[amount];
    }
}
