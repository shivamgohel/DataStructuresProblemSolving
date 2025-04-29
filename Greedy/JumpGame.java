// https://leetcode.com/problems/jump-game/description/
import java.util.*;

class JumpGame
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         You are given an integer array `nums` where each element represents your maximum jump length at that position.
         Return `true` if you can reach the last index starting from the first index; otherwise, return `false`.
        */
       
        int[] nums = {1,2,1,0,1};

        // Brute-force approach: O(n!) time, O(n) stack space
        System.out.println(jumpGameBrute(nums));    

        // Better approach 1 (Top-down DP with memoization): O(n^2) time, O(n) space
        System.out.println(jumpGameBetter1(nums));    

        // Better approach 2 (Bottom-up DP): O(n^2) time, O(n) space
        System.out.println(jumpGameBetter2(nums));    

        // Optimized approach (Greedy): O(n) time, O(1) space
        System.out.println(jumpGameOptimized(nums));    

    }

    /*
     Brute-force Approach:
     - Try all possible paths recursively from the current index to the end.
     
     Approach Steps:
     1. At each index, calculate the farthest jump possible.
     2. Recursively try all positions you can jump to from there.
     3. If any path leads to the last index, return true.

     Time Complexity: O(n!)
     Space Complexity: O(n) (recursive stack)
    */
    private static boolean jumpGameBrute(int[] nums) {
        return canReachEnd(nums, 0);
    }
    private static boolean canReachEnd(int[] nums, int currentIndex) {
        if(currentIndex == nums.length-1)
            return true;

        int farthestJump = Math.min(nums[currentIndex]+currentIndex, nums.length-1);
        for(int nextIndex=currentIndex+1; nextIndex<=farthestJump; nextIndex++)
        {
            if(canReachEnd(nums, nextIndex))
                return true;
        }

        return false;    
    }

    /*
     Better Approach 1 (Top-down DP with Memoization):
     - Cache results for subproblems to avoid recomputation.
     
     Approach Steps:
     1. Same logic as brute-force but store results in a `dp[]` array.
     2. dp[i] = 1 if index i can reach the end, 0 otherwise.

     Time Complexity: O(n^2)
     Space Complexity: O(n)
    */
    private static boolean jumpGameBetter1(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);

        return canReachEnd(nums,0,dp);
    }
    private static boolean canReachEnd(int[] nums, int currentIndex, int[] dp) {
        if(currentIndex == nums.length-1)
            return true;    

        if(dp[currentIndex] != -1)
            return dp[currentIndex] == 1;

        int farthestJump = Math.min(nums[currentIndex]+currentIndex, nums.length-1);
        for(int nextIndex=currentIndex+1; nextIndex<=farthestJump; nextIndex++)
        {
            if(canReachEnd(nums, nextIndex, dp))
            {
                dp[currentIndex] = 1;
                return true;
            }
        }

        dp[currentIndex] = 0;
        return false;   
    }
    
    /*
     Better Approach 2 (Bottom-up DP):
     - Build the solution iteratively from the end to the beginning.
     
     Approach Steps:
     1. Create a boolean dp[] initialized with false.
     2. dp[i] = true means we can reach the end from index i.
     3. Start from last index and work backward.

     Time Complexity: O(n^2)
     Space Complexity: O(n)
    */
    private static boolean jumpGameBetter2(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[nums.length-1] = true;

        for(int i=nums.length-2; i>=0; i--)
        {
            int farthestJump = Math.min(nums[i]+i, nums.length-1);
            for(int j=i+1; j<=farthestJump; j++)
            {
                if(dp[j])
                {
                    dp[i] = true;
                    break;
                }
            } 
        }

        return dp[0];
    }

    /*
     Optimized Approach (Greedy):
     - Track the farthest index reachable as you iterate forward.
     
     Approach Steps:
     1. Iterate from start to end.
     2. At each step, update the farthest you can reach.
     3. If current index exceeds farthest reachable, return false.
     4. If loop ends successfully, return true.

     Time Complexity: O(n)
     Space Complexity: O(1)
    */
    private static boolean jumpGameOptimized(int[] nums) {
        int farthestReachableIndex = 0;
        for(int i=0; i<nums.length; i++)
        {
            if(i > farthestReachableIndex)
                return false;

            farthestReachableIndex = Math.max(farthestReachableIndex, nums[i]+i);    
        }

        return true;
    }
   
}