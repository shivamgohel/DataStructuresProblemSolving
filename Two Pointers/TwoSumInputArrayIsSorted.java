// https://neetcode.io/problems/two-integer-sum-ii
import java.util.*;

class TwoSumInputArrayIsSorted
{
    public static void main(String[] args) 
    {   
        /*
        Problem Statement:
        Given a sorted array of integers, find the two numbers that sum up to a target value.
        Return the indices (1-based) of these two numbers.
        */

        int[] nums = {2,7,11,15};
        int target = 9;

        // Brute-force approach: O(n^2) time complexity, O(1) space complexity
        System.out.println(Arrays.toString(twoSumInputArrayIsSortedBrute(nums,target)));   

        // Binary search approach: O(n log n) time complexity, O(1) space complexity
        System.out.println(Arrays.toString(twoSumInputArrayIsSortedBetter1(nums,target)));  

        // HashMap approach: O(n) time complexity, O(n) space complexity
        System.out.println(Arrays.toString(twoSumInputArrayIsSortedBetter2(nums,target)));

        // Optimized two-pointer approach: O(n) time complexity, O(1) space complexity
        System.out.println(Arrays.toString(twoSumInputArrayIsSortedOptimized(nums,target)));     



    }

    /*
     * Brute-force approach:
     * - Uses two nested loops to check every pair for the target sum.
     * 
     * Time Complexity: O(n^2) - Nested loop iterating through the array.
     * Space Complexity: O(1) - No extra space used.
    */
    private static int[] twoSumInputArrayIsSortedBrute(int[] nums, int target) {
        for(int i=0;i<nums.length;i++)
        {
            for(int j=i+1;j<nums.length;j++)
            {
                if(nums[i] + nums[j] == target)
                    return new int[] {i+1,j+1};
            }
        }

        return new int[] {-1,-1};
    }

    /*
     * Binary Search Approach:
     * - Iterate through the array, considering nums[i] as the first element.
     * - Compute `complement = target - nums[i]`.
     * - Use binary search to find `complement` in the range (i+1, n-1).
     * 
     * Time Complexity: O(n log n) - Binary search for each element.
     * Space Complexity: O(1) - No extra space used.
    */
    private static int[] twoSumInputArrayIsSortedBetter1(int[] nums, int target) {
        for(int i=0;i<nums.length;i++)
        {
            int complement = target - nums[i];
            int left = i + 1;
            int right = nums.length - 1;

            while(left <= right)
            {
                int mid = left + (right-left)/2;

                if(nums[mid] == complement)
                    return new int[]{i+1, mid+1};
                else if(nums[mid] < complement)
                    left = mid + 1;
                else
                    right = mid - 1;        
            }
        }

        return new int[]{-1,-1};
    }

    /*
     * HashMap Approach:
     * - Store each number in a HashMap while iterating.
     * - For each number, check if `target - nums[i]` is already in the HashMap.
     * - If found, return their 1-based indices.
     * 
     * Time Complexity: O(n) - Single pass with HashMap lookups.
     * Space Complexity: O(n) - HashMap stores elements.
    */
    private static int[] twoSumInputArrayIsSortedBetter2(int[] nums, int target) {
        HashMap<Integer,Integer> store = new HashMap<>();

        for(int i=0;i<nums.length;i++)
        {
            int complement = target - nums[i];
            if(store.containsKey(complement) && store.get(complement) != i)
                return new int[]{store.get(complement) +1, i +1};

            store.put(nums[i], i);
        } 

        return new int[]{-1,-1};   
    }

    /*
     * Optimized Two-Pointer Approach:
     * - Uses two pointers: one at the beginning (`left`) and one at the end (`right`).
     * - If nums[left] + nums[right] == target → return indices.
     * - If sum is too small → move `left` forward (increase sum).
     * - If sum is too large → move `right` backward (decrease sum).
     * 
     * Time Complexity: O(n) - Single pass through the array.
     * Space Complexity: O(1) - No extra space used.
    */
    private static int[] twoSumInputArrayIsSortedOptimized(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;

        while(left < right)
        {
            if(nums[left] + nums[right] == target)
                return new int[]{left+1,right+1};
            else if(nums[left] + nums[right] < target)
                left++;
            else
                right--;        
        }

        return new int[]{-1,-1};
    }
}