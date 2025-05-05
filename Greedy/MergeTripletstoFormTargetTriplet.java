// https://leetcode.com/problems/merge-triplets-to-form-target-triplet/description/
import java.util.*;

class MergeTripletstoFormTargetTriplet
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         You are given a list of triplets and a target triplet.
         A triplet can be merged into another triplet if all its values are less than or equal 
         to the corresponding values of the target.
         You can merge any number of valid triplets (i.e., element-wise ≤ target) using 
         the max at each index.
         Return true if by merging any number of triplets, we can form the target triplet.
        */

        int[][] triplets = {{2, 5, 3}, {1, 8, 4}, {1, 7, 5}};
        int[] target = {2, 7, 5};
        
        // Optimized approach using Set: O(n) time, O(1) space
        System.out.println(mergeTripletstoFormTargetTripletOptimized1(triplets, target));

        // Optimized approach using booleans: O(n) time, O(1) space
        System.out.println(mergeTripletstoFormTargetTripletOptimized2(triplets, target));

    }

    /*
     Optimized Approach 1: Using HashSet
     - Skip triplets that exceed the target in any index.
     - For valid triplets, track which target indices are matched.
     - If all three indices (0, 1, 2) are matched across the valid triplets, return true.

     Approach Steps:
     1. Iterate through each triplet.
     2. If any element in the triplet > corresponding target, skip it.
     3. If triplet[i] == target[i], add **index i** (not the value) to a set.
     4. Finally, check if set contains all indices {0, 1, 2}.

     Time Complexity: O(n) — n = number of triplets
     Space Complexity: O(1) — at most 3 elements in the set
    */
    private static boolean mergeTripletstoFormTargetTripletOptimized1(int[][] triplets, int[] target) {
        Set<Integer> set = new HashSet<>();

        for(int[] t : triplets)
        {
            if(t[0] > target[0] || t[1] > target[1] || t[2] > target[2])
                continue;

            for(int i=0;i<t.length;i++)
            {
                if(t[i] == target[i])
                    set.add(i);
            }    
        }

        return set.size() == 3;
    }

    /*
     Optimized Approach 2: Using Boolean Flags
     - Same logic as Approach 1, but avoids using a Set.
     - Use three booleans to track matching indices individually.

     Approach Steps:
     1. Iterate through each triplet.
     2. Skip if triplet exceeds target at any index.
     3. If any index matches the target, set corresponding boolean true.
     4. If all three booleans are true, return true.

     Time Complexity: O(n) — n = number of triplets
     Space Complexity: O(1) — constant space for three flags
    */
    private static boolean mergeTripletstoFormTargetTripletOptimized2(int[][] triplets, int[] target) {
        boolean found0 = false;
        boolean found1 = false;
        boolean found2 = false;

        for(int[] t : triplets)
        {
            if(t[0] > target[0] || t[1] > target[1] || t[2] > target[2])
                continue;

            if(t[0] == target[0])
                found0 = true;
            if(t[1] == target[1])
                found1 = true;
            if(t[2] == target[2])
                found2 = true;        
        }

        return found0 && found1 && found2;
    }
}