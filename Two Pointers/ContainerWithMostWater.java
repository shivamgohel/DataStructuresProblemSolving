// https://leetcode.com/problems/container-with-most-water/description/
// import java.util.*;

class ContainerWithMostWater
{
    public static void main(String[] args) 
    {
        int[] height = {1,7,2,5,4,7,3,6};

        System.out.println(containerWithMostWaterBrute(height));

        System.out.println(containerWithMostWaterOptimized(height));

    }

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