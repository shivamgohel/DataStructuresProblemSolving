// https://leetcode.com/problems/trapping-rain-water/description/
import java.util.*;

class TrappingRainWater
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         Given an array `height` where each element represents the elevation at that index,
         compute how much water can be trapped after raining between the bars.

         Return the total units of water that can be trapped.
        */

        int[] height = {0,2,0,3,1,0,1,3,2,1};
        
        // Brute-force approach: O(n^2) time complexity, O(1) space complexity
        System.out.println(trappingRainWaterBrute(height));

        // Prefix-suffix arrays approach: O(n) time complexity, O(n) space complexity
        System.out.println(trappingRainWaterBetter1(height));

        
        // Stack-based approach: O(n) time complexity, O(n) space complexity
        System.out.println(trappingRainWaterBetter2(height));

        // Two-pointer optimized approach: O(n) time complexity, O(1) space complexity
        System.out.println(trappingRainWaterOptimized(height));

    }

    /*
     Brute-force Approach:
     - For each index, find the tallest bar on the left and right.
     - Water trapped = min(left_max, right_max) - height[i]
     - Accumulate if the result is positive.

     Time Complexity: O(n^2)
     Space Complexity: O(1)

     Drawbacks:
     - Inefficient for large arrays due to nested loops.
    */
    private static int trappingRainWaterBrute(int[] height) {
        if(height == null || height.length == 0)
            return 0;

        int total_water = 0;

        for(int i=0;i<height.length;i++)
        {
            int left_max = height[i];
            int right_max = height[i];

            for(int j=0;j<i;j++)
                left_max = Math.max(left_max, height[j]);
            for(int j=i+1;j<height.length;j++)
                right_max = Math.max(right_max, height[j]);    

            int trapped_water = Math.min(left_max,right_max) - height[i];
            if(trapped_water > 0)
                total_water += trapped_water;
        }
        
        return total_water;
    }

    /*
     Better Approach 1 (Prefix & Suffix Arrays):
     - Precompute max height from the left for each index.
     - Precompute max height from the right for each index.
     - Water trapped = min(left_max[i], right_max[i]) - height[i]

     Time Complexity: O(n)
     Space Complexity: O(n) for two extra arrays

     Much faster than brute-force, easy to understand.
    */
    private static int trappingRainWaterBetter1(int[] height) {
        if(height == null || height.length == 0)
            return 0;
        
        int total_water = 0;
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];

        leftMax[0] = height[0];
        for(int i=1;i<height.length;i++)
            leftMax[i] = Math.max(leftMax[i-1],height[i]);

        rightMax[height.length-1] = height[height.length-1];
        for(int i=height.length-2;i>=0;i--)
            rightMax[i] = Math.max(rightMax[i+1],height[i]);

        for(int i=0;i<height.length;i++)
        {
            int trapped_water = Math.min(leftMax[i],rightMax[i]) - height[i];
            if(trapped_water > 0)
                total_water += trapped_water;
        }

        return total_water;        
    }

    /*
    Better Approach 2 (Using Stack):
    - Use a monotonic stack to find boundaries of valleys.
    - Traverse through the heights from left to right.
    - For each bar, check if it is taller than the bar at the top of the stack.
    - If taller, pop the stack to find the left boundary, then calculate the trapped water between the left and right boundaries.
    - The width of the trapped water is the difference between the current index and the new top of the stack.
    - The bounded height is the minimum of the heights at the current index and the new top of the stack minus the valley height.

    Time Complexity: O(n)
    - Each element is pushed and popped from the stack at most once.

    Space Complexity: O(n)
    - In the worst case, the stack may hold all elements if the heights are strictly increasing or decreasing.

    This approach is space-efficient in comparison to the prefix-suffix approach, as it only uses a single stack. 
    Ideal for situations where memory usage needs to be optimized, and it's a common approach used in interviews.
    */
    private static int trappingRainWaterBetter2(int[] height) {
        if(height == null || height.length == 0)
            return 0;

        int total_water = 0;
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<height.length;i++)
        {
            while(!st.isEmpty() && height[i] >= height[st.peek()])
            {
                int valley_index = st.pop();
                if(!st.isEmpty())
                {
                    int left_index = st.peek();
                    int right_index = i;
                    
                    int width = right_index - left_index - 1;
                    int bounded_height = Math.min(height[left_index], height[right_index]) - height[valley_index];

                    int trapped_water = width * bounded_height;
                    if(trapped_water > 0)
                        total_water += trapped_water; 
                }
            }
            st.push(i);
        }

        return total_water;
    }

    /*
    Optimized Two-Pointer Approach:
    - This approach uses two pointers, one starting at the leftmost index and the other at the rightmost index of the array.
    - We also maintain two variables `left_max` and `right_max`, which represent the maximum heights encountered from the left and right sides, respectively.
    - The goal is to calculate the trapped water between the bars as we move the pointers toward each other.

    How it works:
    - At each step, compare the heights at `left` and `right` pointers:
        1. If the height at `left` is smaller than the height at `right`, calculate the trapped water at `left` using `left_max`. If the current height is less than `left_max`, water is trapped and added to the total.
        2. If the height at `right` is smaller, calculate the trapped water at `right` using `right_max` and move the `right` pointer inward.
    - Move the pointer with the smaller height inwards because water trapping is limited by the shorter bar, and we want to find taller bars to increase the trapped water.

    Time Complexity: O(n) - We iterate through the array only once, making it a linear time solution.
    Space Complexity: O(1) - Only a constant amount of extra space is used (for `left_max`, `right_max`, and pointers).

    This is the most efficient solution for the problem, offering both time and space optimization, making it ideal for real-world applications and interviews.
    */
    private static int trappingRainWaterOptimized(int[] height) {
        if(height == null || height.length == 0)
            return 0;

        int total_water = 0;

        int left_index = 0;
        int left_max = height[left_index];
        
        int right_index = height.length-1;
        int right_max = height[right_index];

        while(left_index < right_index)
        {
            if(left_max < right_max)
            {
                left_index++;
                left_max = Math.max(left_max, height[left_index]);
                total_water += left_max - height[left_index];
            }
            else
            {
                right_index--;
                right_max = Math.max(right_max, height[right_index]);
                total_water += right_max - height[right_index];
            }
        }

        return total_water;
    }

}