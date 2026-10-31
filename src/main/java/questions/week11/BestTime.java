package questions.week11;

/**
 * 121. Best Time to Buy and Sell Stock
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * prices[i] is the price on day i.
 * Pick one day to buy and a later day to sell.
 * Return the biggest profit. If no profit is possible, return 0.
 *
 * Example: [7,1,5,3,6,4] -> 5.
 */
public class BestTime {

    public int maxProfit(int[] prices) {
        if (prices.length<=1) return 0;
        int min=Integer.MAX_VALUE;
        int maxProfit=0;
        for(int x:prices){
            if(min>x) min=x;
            if(x>min && x-min>maxProfit) maxProfit=x-min;
        }
        return maxProfit;
    }
}
