// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
// import java.util.*;

class BestTimetoBuySellStock
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         You are given an array `prices` where `prices[i]` is the price of a given stock on the i-th day.
         You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
         Return the maximum profit you can achieve from this transaction. If no profit is possible, return 0.
        */

        int[] prices = {7,1,5,3,6,4};

        // Brute-force approach: O(n^2) time, O(1) space
        System.out.println(bestTimeToBuySellStockBrute(prices));

        // Optimized approach 1: O(n) time, O(1) space  {Kadane Algorithm}
        System.out.println(bestTimeToBuySellStockOptimized1(prices));

        // Optimized approach 2 (Two pointers): O(n) time, O(1) space {Sliding Window}
        System.out.println(bestTimeToBuySellStockOptimized2(prices));


    }

    /*
     Brute-force Approach:
     - Try every possible pair (i, j) such that i < j.
     - Buy on day i, sell on day j, and calculate profit.
     - Track the maximum profit.

     Time Complexity: O(n^2)
     Space Complexity: O(1)

     Drawbacks:
     - Very slow for large input sizes due to nested loops.
    */
    private static int bestTimeToBuySellStockBrute(int[] prices) {
        int max_profit = 0;
        for(int i=0;i<prices.length;i++)
        {
            int buy = prices[i];
            for(int j=i+1;j<prices.length;j++)
            {
                int sell = prices[j];
                max_profit = Math.max(max_profit, sell-buy);
            }
        }

        return max_profit;
    }

    /*
     Optimized Approach 1 (Tracking Minimum):
     - Iterate through the array once.
     - Track the minimum price so far (best day to buy).
     - At each step, calculate profit if you sold today.
     - Update max profit accordingly.

     Time Complexity: O(n)
     Space Complexity: O(1)
    */
    private static int bestTimeToBuySellStockOptimized1(int[] prices) {
        int max_profit = 0;
        int buy = prices[0];

        for(int price : prices)
        {
            buy = Math.min(buy, price);
            max_profit = Math.max(max_profit, price - buy);
        }

        return max_profit;
    }
    
    /*
     Optimized Approach 2 (Two Pointer):
     - Use two pointers: left = buy day, right = sell day.
     - If prices[right] > prices[left], calculate profit.
     - Else, move left to right (better buying opportunity).
     - Move right forward in every case.

     Time Complexity: O(n)
     Space Complexity: O(1)

     Intuition:
     - We always want to buy low and sell high.
     - By moving left/right strategically, we track best opportunity.
    */
    private static int bestTimeToBuySellStockOptimized2(int[] prices){
        int max_profit = 0;

        int left = 0;
        int right = 1;

        while(right < prices.length)
        {
            if(prices[left] < prices[right])
                max_profit = Math.max(max_profit, prices[right] - prices[left]);
            else
                left = right;    
            
            right++;
        }

        return max_profit;
    }

}