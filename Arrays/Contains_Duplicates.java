import java.util.*;

public class Contains_Duplicates
{
    public static void main(String[] args)
    {
        int nums[] = {1,2,3,3};

        // Brute-force approach: O(n²) time complexity O(1), space complexity
        System.out.println(conatinsDuplicateBrute(nums));

        // Sorting-based approach: O(n log n) time complexity, O(1) space complexity
        System.out.println(conatinsDuplicateBetter(nums));

        // HashSet-based approach (early exit): O(n) time complexity, O(n) space complexity
        System.out.println(conatinsDuplicateOptimzed1(nums));

        // HashSet-based approach (size comparison): O(n) time complexity, O(n) space complexity
        System.out.println(conatinsDuplicateOptimzed2(nums));
    }
    
    /*
     * Brute-force approach: Compare every element with every other element
     * Time Complexity: O(n²) - Nested loops
     * Space Complexity: O(1) - No extra data structures used
    */
    private static boolean conatinsDuplicateBrute(int[] nums) {
        for(int i=0;i<nums.length;i++)
        {
            for(int j=i+1;j<nums.length;j++)
            {
                if(nums[i] ==  nums[j])
                    return true;
            }
        }
        return false;
    }

    /*
     * Sorting-based approach: Sorts array first, then checks adjacent elements
     * Time Complexity: O(n log n) - Due to sorting
     * Space Complexity: O(1) - Sorting is in-place
    */
    private  static boolean conatinsDuplicateBetter(int[] nums) {
        Arrays.sort(nums);
        
        for(int i=1;i<nums.length;i++)
        {
            if(nums[i-1] == nums[i])
                return true;
        }
        return false;
    }

    /*
     * HashSet-based approach (early exit): Adds elements to a HashSet and checks for duplicates
     * Time Complexity: O(n) - Each lookup and insertion in HashSet is O(1) on average
     * Space Complexity: O(n) - Uses extra space for HashSet storage
    */
    private static boolean conatinsDuplicateOptimzed1(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();

        for(int x : nums)
        {
            if(seen.contains(x))
                return true;
            seen.add(x);    
        }
        return false; 
    }

    /*
     * HashSet-based approach (size comparison): Stores all elements in HashSet, then checks size
     * Time Complexity: O(n) - Each insertion in HashSet is O(1) on average
     * Space Complexity: O(n) - Uses extra space for HashSet storage
     * Drawback: Always processes the entire array, even if duplicate exists early
    */
    private static boolean conatinsDuplicateOptimzed2(int[] nums) {
        HashSet<Integer> seen2 = new HashSet<>();

        for(int x : nums)
            seen2.add(x);

        return nums.length == seen2.size();    
    }


}