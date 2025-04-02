import java.util.*;

class LongestConsecutiveSequence 
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
        */
        
        int[] nums = {1,2,3,7,8,9,10};

        // Brute force approach using a HashSet (Nested Loops)
        System.out.println(longestConsecutiveSequenceBrute(nums));
        
        // Better approach using sorting (Sorting and linear scan)
        System.out.println(longestConsecutiveSequenceBetter(nums));
        
        // Optimized approach using a HashSet (One pass)
        System.out.println(longestConsecutiveSequenceOptimized(nums));
    }

    /*
     * Approach 1: Brute Force (HashSet to store elements)
     * Time Complexity: O(n²) -> The inner while loop may iterate n times for each element.
     * Space Complexity: O(n) -> We use a HashSet to store the elements.
     * - Add all elements to a HashSet for O(1) lookups.
     * - For each element in the array, check how long the consecutive streak is starting from that element.
     * - Increment the streak while consecutive numbers are found in the HashSet.
    */
    private static int longestConsecutiveSequenceBrute(int[] nums) {
        if (nums.length == 0)
            return 0;

        int res = 1;        

        // HashSet to store unique elements
        Set<Integer> store = new HashSet<>();
        for (int num : nums)
            store.add(num);

        for (int i = 0; i < nums.length; i++) {
            int streak = 0;
            int current_element = nums[i];
            while (store.contains(current_element)) {
                streak++;
                current_element++;
            }

            res = Math.max(res, streak);
        }

        return res;
    }

    /*
     * Approach 2: Better approach using sorting
     * Time Complexity: O(n log n) -> Sorting takes O(n log n), linear scan is O(n).
     * Space Complexity: O(1) -> We are not using extra space, just modifying the input array.
     * - First, sort the array.
     * - Then, iterate through the sorted array to find the longest consecutive streak.
     * - Handle duplicates during the iteration to avoid counting them as separate streaks.
    */
    private static int longestConsecutiveSequenceBetter(int[] nums) {
        if (nums.length == 0)
            return 0;

        int res = 1;
        int streak = 1;
        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1])
                continue;

            if (nums[i] == nums[i - 1] + 1)
                streak++;
            else
                streak = 1;

            res = Math.max(res, streak);
        }   

        return res;
    }

    /*
     * Approach 3: Optimized approach using HashSet (One pass)
     * Time Complexity: O(n) -> Only a single pass through the array is required.
     * Space Complexity: O(n) -> We use a HashSet to store the elements.
     * - Insert all elements into a HashSet for O(1) lookups.
     * - For each element, check if it's the start of a consecutive sequence (num-1 should not exist).
     * - If it is, check how long the consecutive sequence is starting from that element.
    */
    private static int longestConsecutiveSequenceOptimized(int[] nums) {
        if(nums.length == 0)
            return 0;
        
        int res = 1;

        Set<Integer> store = new HashSet<>();
        for(int num : nums)
            store.add(num);

        // TRAVERSE ON SET INSTEAD OF ORIGINAL ARRAY (nums) 
        for(int num : store)
        {
            if(store.contains(num - 1))
                continue;

            int streak = 1;
            while(store.contains(num + streak))
            {
                streak++;
            }
            res = Math.max(res, streak); 
        }

        return res;    
    }
}
