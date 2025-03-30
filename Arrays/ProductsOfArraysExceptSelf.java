import java.util.*;

class ProductsOfArraysExceptSelf
{
    public static void main(String[] args) 
    {
        /*
        Problem Statement:
        Given an array nums, return an array where each element at index i is the product of 
        all elements in nums except nums[i].
        */

        int[] nums = {1,2,3,4};
        // productArrayExceptSelf[] - {24,12,8,6}

        // Brute Force (Nested Loops)
        System.out.println(Arrays.toString(productsOfArraysExceptSelfBrute(nums)));
        
        // Optimized using Prefix and Suffix Products (Extra Space)
        System.out.println(Arrays.toString(productsOfArraysExceptSelfOptimized1(nums)));
        
        // Optimized using Prefix and Suffix Products (Constant Space)
        System.out.println(Arrays.toString(productsOfArraysExceptSelfOptimized2(nums)));
        
        // Division-based Approach (Handles Zeros)
        System.out.println(Arrays.toString(productsOfArraysExceptSelfExtra(nums)));

    }

    /*
     * Approach 1: Brute Force (Nested Loops)
     * Time Complexity: O(n^2) - Each element requires a full traversal.
     * Space Complexity: O(1) - Uses only the result array.
     * - For each element in nums, calculate the product of all other elements.
     * - Use a nested loop to iterate through all elements except the current index.
    */
    private static int[] productsOfArraysExceptSelfBrute(int[] nums) {
        
        int[] res = new int[nums.length];
        
        for(int i=0;i<nums.length;i++)
        {   
            int product = 1;
            for(int j=0;j<nums.length;j++)
            {
                if(i != j)
                    product *= nums[j];
            }
            res[i] = product;
        }

        return res;
    }

    /*
     * Approach 2: Prefix & Suffix Products (Extra Space)
     * Time Complexity: O(n) - Two passes to compute prefix and suffix arrays.
     * Space Complexity: O(n) - Uses two extra arrays (prefix & suffix).
     * - Compute prefix product for each index.
     * - Compute suffix product for each index.
     * - Multiply prefix and suffix values for the final result.
    */
    private static int[] productsOfArraysExceptSelfOptimized1(int[] nums) {
        int[] res = new int[nums.length];
        
        int[] prefix_product_arr = new int[nums.length];
        prefix_product_arr[0] = 1;
        int[] suffix_product_arr = new int[nums.length];
        suffix_product_arr[nums.length-1] = 1;

        for(int i=1;i<nums.length;i++)
            prefix_product_arr[i] = prefix_product_arr[i-1] * nums[i-1];

        for(int i=nums.length-2;i>=0;i--)
            suffix_product_arr[i] = suffix_product_arr[i+1] * nums[i+1];

        for(int i=0;i<nums.length;i++)
            res[i] = prefix_product_arr[i] * suffix_product_arr[i];

        return res;     
    }

    /*
     * Approach 3: Prefix & Suffix Products (Constant Space)
     * Time Complexity: O(n) - Two passes, one for prefix and one for suffix.
     * Space Complexity: O(1) - Uses only the output array.
     * - Store prefix products directly in result array.
     * - Iterate from the end to compute suffix products and multiply in place.
    */
    private static int[] productsOfArraysExceptSelfOptimized2(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;

        for(int i=1;i<nums.length;i++)
            res[i] = res[i-1] * nums[i-1];

        int postfix = 1;
        for(int i=nums.length-1;i>=0;i--)
        {    
            res[i] *= postfix;
            postfix *= nums[i];
        }
        
        return res;
    }

    /*
     * Approach 4: Division-Based Approach (Handles Zeros)
     * Time Complexity: O(n) - Two passes to compute product and handle zeros.
     * Space Complexity: O(1) - Uses only a few extra variables.
     * - If there are no zeros, divide the total product by each element.
     * - If exactly one zero exists, set its index to the product of non-zero elements.
     * - If multiple zeros exist, the entire result array is zero.
    */
    private static int[] productsOfArraysExceptSelfExtra(int[] nums) {
        int count_of_zero = 0;
        int product = 1;

        for(int x : nums)
        {
            if(x==0)
                count_of_zero++;
            else
                product *= x;    
        }

        int[] res = new int[nums.length];
        for(int i =0;i<nums.length;i++)
        {
            if(count_of_zero == 0)
            {
                res[i] = product / nums[i];           // Safe division when no zeros.
            }
            else if(count_of_zero == 1)
            {
                if(nums[i] == 0) res[i] = product;    // Only zero index gets the product.
            }
            else
            {
                return res;                           // More than one zero, all values remain zero.
            }
        }    

        return res;
    
    }
}