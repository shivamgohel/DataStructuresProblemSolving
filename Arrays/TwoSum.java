// https://leetcode.com/problems/two-sum/description/
import java.util.*;

class TwoSum
{
    public static void main(String[] args) 
    {
        /*
        Problem Statement:
        Given an array nums and an integer target, 
        return the indices of the two numbers that add up to target.
        */

        int[] nums = {0,4,6,5};
        int target = 7;

        // Java does not override toString() for arrays. (prints the address of array)
        // Arrays.toString(array) converts the array into a human-readable string format.
        // It prints the elements inside square brackets.
        
        // Brute force approach (Nested loops)
        System.out.println(Arrays.toString(twoSumBrute(nums,target)));

        // Optimized approach using HashMap (Two pass)
        System.out.println(Arrays.toString(twoSumOptimized1(nums,target))); 

        // Optimized approach using HashMap (Single pass)
        System.out.println(Arrays.toString(twoSumOptimized2(nums, target)));

      
    }

    /*
     * Approach 1: Brute Force (Nested Loops)
     * Time Complexity: O(n²) -> Checks all pairs.
     * Space Complexity: O(1) -> No extra space used.
     * - Iterate through every pair of numbers in the array.
     * - If a pair sums up to the target, return their indices.
    */
    private static int[] twoSumBrute(int[] nums, int target) {
        for(int i=0;i<nums.length;i++)
        {
            for(int j=i+1;j<nums.length;j++)
            {
                if((nums[i] + nums[j]) == target)
                    return new int[]{i , j};
            }
        }
        return new int[]{-1,-1};
    }
    
    /*
     * Approach 2: HashMap (Two-pass solution)
     * Time Complexity: O(n) -> Two separate loops (O(n) + O(n) = O(n)).
     * Space Complexity: O(n) -> Stores all elements in HashMap.
     * - First, store all elements in a HashMap with their indices.
     * - Then, iterate through the array again to check for the complement (target - current number).
    */
    private static int[] twoSumOptimized1(int[] nums, int target) {
        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int i=0;i<nums.length;i++)
        {
            hm.put(nums[i], i);
        }

        for(int i=0;i<nums.length;i++)
        {
            int diff = target - nums[i];
            if(hm.containsKey(diff) && hm.get(diff) != i)
            {
                return new int[]{i , hm.get(diff)};
            }
        }

        return new int[]{-1,-1};
    }

    /*
     * Approach 3: HashMap (Single-pass solution)
     * Time Complexity: O(n) -> Each element is processed once.
     * Space Complexity: O(n) -> Stores elements in HashMap.
     * - Iterate through the array once, checking if the complement (target - current number) exists in the HashMap.
     * - If found, return the indices immediately.
     * - If not, store the current number and its index in the HashMap.
    */
    private static int[] twoSumOptimized2(int[] nums, int target) {
        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int i=0;i<nums.length;i++)
        {
            int diff = target - nums[i];
            if(hm.containsKey(diff) && hm.get(diff) != i)
            {
                return new int[]{hm.get(diff) , i};
            }

            hm.put(nums[i], i);
        }
        return new int[]{-1,-1};
    }


}