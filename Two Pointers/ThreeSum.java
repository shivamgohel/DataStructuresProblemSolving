// https://leetcode.com/problems/3sum/description/
import java.util.*;

class ThreeSum
{
    public static void main(String[] args) {
        
        /*
        Problem Statement:
        Given an integer array nums, return all unique triplets [nums[i], nums[j], nums[k]] 
        such that i != j != k and nums[i] + nums[j] + nums[k] == 0.

        Return the triplets in any order.
        */
        
        int[] nums = {-1,0,1,2,-1,-4};

        // Brute-force approach: O(n^3) time complexity, O(1) space complexity
        System.out.println(threeSumBrute(nums));

        // Hashing approach: O(n^2) time complexity, O(n) space complexity
        System.out.println(threeSumBetter(nums));

        // Optimized two-pointer approach: O(n^2) time complexity, O(1) space complexity
        System.out.println(threeSumOptimized(nums));


    }

    /*
     * Brute-force approach:
     * - Use three nested loops to check every triplet.
     * - If the sum of nums[i], nums[j], and nums[k] is zero, add it to the result set.
     * - Convert the set to a list to ensure unique triplets.
     * 
     * Time Complexity: O(n^3) - Three nested loops iterate over all triplets.
     * Space Complexity: O(1) 
    */
    private static List<List<Integer>> threeSumBrute(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();

        for(int i=0;i<nums.length;i++)
        {
            for(int j=i+1;j<nums.length;j++)
            {
                for(int k=j+1;k<nums.length;k++)
                {
                    if((nums[i] + nums[j] + nums[k]) == 0)
                    {
                        List<Integer> temp = Arrays.asList(nums[i],nums[j],nums[k]);
                        temp.sort(null);
                        res.add(temp);
                    }
                }
            }
        }

        return new ArrayList<>(res);    
    }

    /*
     * Hashing Approach:
     * - Iterate through the array, fixing one element (nums[k]).
     * - Use a HashSet to store previously seen elements.
     * - Check if `-(nums[i] + nums[j])` exists in the HashSet.
     * - Store triplets in a set to remove duplicates.
     * 
     * Time Complexity: O(n^2) - Outer loop runs O(n) times, inner loop runs O(n).
     * Space Complexity: O(n) - HashSet stores seen elements.
    */
    private static List<List<Integer>> threeSumBetter(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();

        for(int i=0;i<nums.length;i++)
        {   
            Set<Integer> store = new HashSet<>();
            for(int j=i+1;j<nums.length;j++)
            {
                int third = -(nums[i] + nums[j]);

                if(store.contains(third))
                {
                    List<Integer> temp = Arrays.asList(nums[i],nums[j],third);
                    temp.sort(null);
                    res.add(temp);
                }

                store.add(nums[j]);
            }
        }

        return new ArrayList<>(res);
    }

    /*
     * Optimized Two-Pointer Approach:
     * - First, **sort** the array to allow efficient two-pointer traversal.
     * - Iterate through the array, fixing **one element** at `first` (nums[first]).
     *   - `first` starts from index 0 and moves towards the end.
     * - Use **two pointers** (`second` and `third`) to find the other two elements:
     *   - `second` starts from `first + 1` (just after `first`).
     *   - `third` starts from the last index (nums.length - 1).
     * - Move pointers based on the sum condition:
     *   - If `nums[first] + nums[second] + nums[third] < 0`, move `second` forward (increase sum).
     *   - If `nums[first] + nums[second] + nums[third] > 0`, move `third` backward (decrease sum).
     *   - If the sum is **exactly zero**, add the triplet to the result set:
     *     - Move `second` forward and `third` backward.
     *     - Skip duplicate values for both pointers to avoid repeated triplets.
     * 
     * Time Complexity: O(n^2) - Outer loop runs O(n), two-pointer search runs O(n).
     * Space Complexity: O(1) 
    */
    private static List<List<Integer>> threeSumOptimized(int[] nums) {
        Arrays.sort(nums);

        Set<List<Integer>> res = new HashSet<>();

        for(int first=0;first<nums.length;first++)
        {
            if(first > 0 && nums[first] == nums[first-1])
                continue;

            int second = first + 1;
            int third =  nums.length-1;  

            while(second < third)
            {
                int sum = nums[first] + nums[second] + nums[third];
                
                if(sum < 0)
                    second++;
                else if(sum > 0)
                    third--;
                else
                {
                    List<Integer> temp = Arrays.asList(nums[first],nums[second],nums[third]);
                    res.add(temp);

                    second++;
                    third--;

                    while(second < third && nums[second] == nums[second-1])
                        second++;
                    while(second < third && nums[third] == nums[third+1])
                        third--;    
                }
            }
        }

        return new ArrayList<>(res);
    }
}