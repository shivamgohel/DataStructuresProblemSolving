// https://leetcode.com/problems/maximum-subarray/description/
import java.util.*;

class MaximumSubarray
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         Given an integer array `nums`, find the contiguous subarray (containing at least one number) 
         which has the largest sum and return its sum.
        */

        int nums[] = {2,-3,4,-2,2,1,-1,4};

        // Brute-force approach: O(n^2) time, O(1) space
        System.out.println(maximumSubarrayBrute(nums));

        // Better approach (using DP array): O(n) time, O(n) space
        System.out.println(maximumSubarrayBetter(nums));

        // Optimized approach (Kadane’s Algorithm): O(n) time, O(1) space
        System.out.println(maximumSubarrayOptimized(nums));
        
       
        // Follow-up: Return the actual subarray with maximum sum
        System.out.println(Arrays.toString(maximumSubarrayFollowUp(nums)));



    }

    /*
     Brute-force Approach:
     - This approach checks all possible subarrays and calculates their sums.
     - It updates the maximum sum found across all subarrays.

     Approach Steps:
     1. Use two nested loops:
        - Outer loop `i` sets the start index.
        - Inner loop `j` adds up elements from `i` to `j`.
     2. Update `maximumSum` whenever a larger sum is found.
     3. Return `maximumSum` after checking all subarrays.

     Time Complexity: O(n^2) — two nested loops over array elements.
     Space Complexity: O(1) — only variables are used.
    */
    private static int maximumSubarrayBrute(int[] nums) {
        if(nums.length == 0)
            return -1;
        
        int maximumSum = Integer.MIN_VALUE;

        for(int i=0;i<nums.length;i++)
        {
            int currentSum = 0;
            for(int j=i;j<nums.length;j++)
            {
                currentSum += nums[j];
                maximumSum = Math.max(maximumSum, currentSum);
            }
        } 

        return maximumSum;
    }

    /*
     Better Approach: Dynamic Programming
     - We use a `dp` array where `dp[i]` represents the maximum subarray sum ending at index `i`.
     - At each index, we decide:
        - Either start a new subarray from current element.
        - Or extend the previous subarray.

     Approach Steps:
     1. Initialize `dp[0]` to `nums[0]`.
     2. For each index `i`, set:
        - dp[i] = max(nums[i], nums[i] + dp[i-1])
     3. After filling `dp` array, the maximum value in `dp` is the answer.

     Time Complexity: O(n) — single pass through array.
     Space Complexity: O(n) — extra `dp` array of size n.
    */
    private static int maximumSubarrayBetter(int[] nums) {
        if(nums.length == 0)
            return -1;

        int maximumSum = Integer.MIN_VALUE;
        
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for(int i=1;i<nums.length;i++)
            dp[i] = Math.max(nums[i], nums[i] + dp[i-1]);

        for(int i=0;i<nums.length;i++)
            maximumSum = Math.max(maximumSum, dp[i]);

        return maximumSum;        
    }

    /*
     Optimized Approach: Kadane's Algorithm
     - Instead of using extra space, we optimize the better approach.
     - We track the running sum and reset it to 0 if it becomes negative.
     - Always keep track of the maximum sum seen so far.

     Approach Steps:
     1. Initialize `currentSum` to 0 and `maximumSum` to minimum value.
     2. For each element:
        - Add it to `currentSum`.
        - Update `maximumSum` if `currentSum` is larger.
        - If `currentSum` becomes negative, reset it to 0.

     Time Complexity: O(n) — single pass.
     Space Complexity: O(1) — no extra space.
    */
    private static int maximumSubarrayOptimized(int[] nums) {
        if(nums.length == 0)
            return -1;
        
        int maximumSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for(int num : nums)
        {
            currentSum += num;
            maximumSum = Math.max(maximumSum, currentSum);
            currentSum = Math.max(currentSum, 0);
        }    

        return maximumSum;
    }

    /*
     Follow-up: Return the actual subarray having maximum sum
     - In addition to tracking maximum sum, we also track the start and end indices.
     - Reset start whenever `currentSum` becomes negative.

     Approach Steps:
     1. Maintain variables:
        - `start` (temporary start)
        - `ansStart` and `ansEnd` (for final answer)
     2. Update `ansStart` and `ansEnd` whenever a new maximum is found.
     3. Return the subarray using these indices.

     Time Complexity: O(n) — single pass.
     Space Complexity: O(k) — size of result subarray.
    */
    private static int[] maximumSubarrayFollowUp(int[] nums) {

        int maximumSum = Integer.MIN_VALUE;
        int currentSum = 0;

        int start = 0;
        int ansStart = 0;
        int ansEnd = 0;    

        for(int i=0;i<nums.length;i++)
        {
            currentSum += nums[i];

            if(currentSum > maximumSum)
            {
                maximumSum = currentSum;
                ansStart = start;
                ansEnd = i;
            }

            if(currentSum < 0)
            {
                currentSum = 0;
                start = i + 1;
            }
        }

        int[] result = new int[ansEnd - ansStart + 1];
        for(int i=ansStart; i<= ansEnd; i++)
        {
            result[i-ansStart] = nums[i];
        }

        return result;
    }
}