// https://leetcode.com/problems/jump-game-ii/description/
import java.util.*;

class JumpGame2
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         Given an array `nums`, where each element represents your maximum jump length from that position,
         return the **minimum number of jumps** needed to reach the last index.
         If it's not possible to reach the end, return -1.
        */
        
        int[] nums = {2,3,1,1,4};
        
        // Brute-force (Recursion): O(n!) time, O(n) space (stack)
        System.out.println(JumpGame2Brute(nums));

        // Better 1 (Recursion + Memoization): O(n^2) time, O(n) space
        System.out.println(JumpGame2Better1(nums));

        // Better 2 (Bottom-Up DP): O(n^2) time, O(n) space
        System.out.println(JumpGame2Better2(nums));

        // Optimized (Greedy BFS): O(n) time, O(1) space
        System.out.println(JumpGame2Optimized(nums));

    }

    /*
     Brute-force (Recursion):
     - Try every possible jump from the current index recursively.

     Approach Steps:
     1. Base Case: If index reaches or exceeds end → 0 jumps needed.
     2. Try all valid next jumps (from i+1 to i+nums[i]) and recursively get minimum.
     3. If a jump leads to an invalid path (returns Integer.MAX_VALUE), skip it.
     
     Time Complexity: O(2^n) — exponential due to recomputation.
     Space Complexity: O(n) — recursion stack depth.
    */
    private static int JumpGame2Brute(int[] nums) {
        int result = minJumps(nums, 0);
        return result == Integer.MAX_VALUE ? -1 : result;  
    }
    private static int minJumps(int[] nums, int currentIndex){
        if(currentIndex >= nums.length-1)
            return 0;
        
        if(nums[currentIndex] == 0)
            return Integer.MAX_VALUE;

        int res = Integer.MAX_VALUE;

        int farthestJump = Math.min(currentIndex+nums[currentIndex], nums.length-1);
        for(int nextIndex=currentIndex+1; nextIndex<=farthestJump; nextIndex++)
        {
            int jumps = minJumps(nums, nextIndex);
            if(jumps != Integer.MAX_VALUE)
                res = Math.min(res, jumps + 1);
        }

        return res;        
    }

    /*
     Better 1 (Memoization):
     - Same as brute-force, but we cache results using a DP array.

     Approach Steps:
     1. Use `dp[i]` to store min jumps from index `i`.
     2. Reuse if already computed.
     3. Recursively solve and store results.

     Time Complexity: O(n^2)
     Space Complexity: O(n)
    */
    private static int JumpGame2Better1(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        
        int result = minJumps(nums, dp, 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    private static int minJumps(int[] nums, int[] dp, int currentIndex){
        if(currentIndex >= nums.length-1)
            return 0;
        
        if(nums[currentIndex] == 0)
            return Integer.MAX_VALUE;

        if(dp[currentIndex] != -1)
            return dp[currentIndex];

        int res = Integer.MAX_VALUE;

        int farthestJump = Math.min(currentIndex+nums[currentIndex], nums.length-1);
        for(int nextIndex=currentIndex+1; nextIndex<=farthestJump; nextIndex++)
        {
            int jumps = minJumps(nums, dp, nextIndex);
            if(jumps != Integer.MAX_VALUE)
                res = Math.min(res, jumps + 1);
        }            

        dp[currentIndex] = res;
        return res;
    }

    /*
     Better 2 (Bottom-Up DP):
     - Iteratively fill `dp[i]` from end to start.

     Approach Steps:
     1. Initialize `dp[n-1] = 0` (no jumps needed from end).
     2. For each position `i`, check all reachable positions `j` and minimize `dp[i] = min(dp[j]+1)`.

     Time Complexity: O(n^2)
     Space Complexity: O(n)
    */
    private static int JumpGame2Better2(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[nums.length-1] = 0;

        for(int i=nums.length-2; i>=0; i--)
        {
            int farthestJump = Math.min(i+nums[i], nums.length-1);
            for(int j=i+1; j<=farthestJump; j++)
            {
                if(dp[j] != Integer.MAX_VALUE)
                    dp[i] = Math.min(dp[i], dp[j] + 1);
            }
        }

        return dp[0] == Integer.MAX_VALUE ? -1 : dp[0];
    }

    /*
     Optimized (Greedy/BFS-like):
     - Traverse the array layer-by-layer (similar to BFS levels).
     - At each jump (layer), compute the farthest index we can reach from the current range.
     - This ensures the minimum number of jumps to reach the end.

     Approach Steps:
     1. Maintain two pointers: `left` and `right` that define the current jump range.
     2. Within each range [left, right], iterate and compute the farthest index reachable (like expanding BFS level).
     3. After exploring the current range, move to the next range [right + 1, farthest] and increment the jump counter.
     4. Repeat until the `right` pointer reaches or surpasses the last index.

     Time Complexity: O(n)       
     Space Complexity: O(1)      
    */
    private static int JumpGame2Optimized(int[] nums) {
        if(nums.length == 1)
            return 0;
        if(nums[0] == 0)
            return -1;

        int jumps = 0;
        int left = 0, right = 0;

        while(right < nums.length-1)
        {
            int farthestJump = 0;
            for(int i=left; i<=right; i++)
                farthestJump = Math.max(farthestJump, i + nums[i]);

            left = right + 1;
            right = farthestJump;
            jumps++;
        }

        return jumps;    
    }
}