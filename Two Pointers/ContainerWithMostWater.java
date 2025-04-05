// https://leetcode.com/problems/container-with-most-water/description/
// import java.util.*;

class ContainerWithMostWater
{
    public static void main(String[] args) 
    {
        /*
        Problem Statement:
        Given an array `height` where height[i] represents the height of a vertical line at index `i`,
        find two lines that together with the x-axis form a container that holds the most water.

        Return the maximum amount of water a container can store.
        */
       
        int[] height = {1,7,2,5,4,7,3,6};

        // Brute-force approach: O(n^2) time complexity, O(1) space complexity 
        System.out.println(containerWithMostWaterBrute(height));

        // Optimized two-pointer approach: O(n) time complexity, O(1) space complexity 
        System.out.println(containerWithMostWaterOptimized(height));

    }

    /*
     * Brute-force Approach:
     * - Use two nested loops to check all possible pairs of lines.
     * - Compute the area using the formula: min(height[i], height[j]) * (j - i).
     * - Keep track of the maximum area found.
     * 
     * Time Complexity: O(n^2) - Two nested loops iterate over all pairs.
     * Space Complexity: O(1) - No extra space is used.
     * 
     * Drawbacks:
     * - Inefficient for large inputs due to O(n^2) complexity.
    */
    private static int containerWithMostWaterBrute(int[] height){
        int max_area = 0;

        for(int i=0;i<height.length;i++)
        {
            for(int j=i+1;j<height.length;j++)
            {
                int current_area = Math.min(height[i],height[j]) * (j-i);
                max_area = Math.max(max_area, current_area);
            }
        }

        return max_area;
    }

    /*
     * Optimized Two-Pointer Approach:
     * - Use two pointers: one at the leftmost index and one at the rightmost index.
     * - Calculate the area and update the max_area if it's greater.
     * - Move the pointer pointing to the shorter height to find a potentially larger area.
     * - Repeat until the pointers meet.
     * 
     * Time Complexity: O(n) - Single pass through the array.
     * Space Complexity: O(1) - No extra space is used.
    */
    private static int containerWithMostWaterOptimized(int[] height) {
        int max_area = 0;
        int left = 0;
        int right = height.length-1;
        
        while(left < right)
        {
            int current_area = Math.min(height[left], height[right]) * (right-left);
            max_area = Math.max(max_area, current_area);

            if(height[left] <= height[right])
                left++;
            else
                right--;    
        }

        return max_area;
    }
}