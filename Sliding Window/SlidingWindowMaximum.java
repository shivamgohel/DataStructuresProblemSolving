// https://leetcode.com/problems/sliding-window-maximum/description/
import java.util.*;

class SlidingWindowMaximum
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         Given an array `nums` and a window size `k`, return an array of the maximum values in every sliding window of size `k`.
        */

        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;

        // Brute-force approach: O(n*k) time, O(1) space
        System.out.println(Arrays.toString(slidingWindowMaximumBrute(nums, k)));

        // Better approach (Heap): O(n log n) time, O(n) space
        System.out.println(Arrays.toString(slidingWindowMaximumBetter(nums, k)));

        // Optimized approach (Deque): O(n) time, O(n) space
        System.out.println(Arrays.toString(slidingWindowMaximumOptimized(nums, k)));


    }

    /*
     Brute-force Approach:
     - For each window of size `k`, find the maximum by scanning all elements.
     
     Approach Steps:
     1. Iterate from index `i = 0` to `n-k`.
     2. For each window, scan `k` elements to find the maximum.
     3. Store the maximum in the result array.

     Time Complexity: O(n * k)
     Space Complexity: O(1)
    */
    private static int[] slidingWindowMaximumBrute(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        
        for(int i=0;i<=nums.length-k;i++)
        {
            int max = nums[i];
            for(int j=i;j<i+k;j++)
            {
                max = Math.max(max, nums[j]);
            }

            res[i] = max;

        }

        return res;
    }

    /*
     Better Approach (Heap):
     - Use a max-heap (priority queue) to track the maximum element inside the window.
     
     Approach Steps:
     1. Heap (priority queue) self-organizes automatically (max-heap or min-heap).
     ➔ So we simply add elements — heap will always keep the maximum on top, even if outdated elements are still inside.
     ➔ Later, we manually check and remove any outdated indices from the heap.
     2. Push the current index into the heap.
     3. Remove the indices which are outside the current window [i-k].
     4. After forming the first full window (i >= k-1), the maximum is at heap's top.

     Time Complexity: O(n log n)
     Space Complexity: O(n)
    */
    private static int[] slidingWindowMaximumBetter(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> nums[b] - nums[a]);

        for(int i=0;i<nums.length;i++)
        {
            maxHeap.offer(i);

            while(maxHeap.peek() <= i - k)
                maxHeap.poll();

            if(i >= k - 1)                             // if (i - k + 1 >= 0) (same)
                res[i - k + 1] = nums[maxHeap.peek()];
        }

        return res;
    }

    /*
     Optimized Approach (Deque):
     - Use a deque to maintain useful elements' indices in decreasing order.
     
     Approach Steps:
     1. Deque is just a manual list — it doesn't self-organize.
     ➔ So we must manually clean it:
         - First remove indices which are outside the window [i-k].
         - Then remove elements from back which are smaller than the current element (because they can't be maximum anymore).
     2. Add the current index to the deque.
     3. After forming a full window (i >= k-1), the maximum is at deque's front.

     Time Complexity: O(n)
     Space Complexity: O(n)
    */
    private static int[] slidingWindowMaximumOptimized(int[] nums, int k) {
        int[] res = new int[nums.length - k +1];

        Deque<Integer> deque = new LinkedList<>();

        for(int i=0;i<nums.length;i++)
        {
            while(!deque.isEmpty() && deque.peekFirst() <= i - k)
                deque.pollFirst();

            while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[i])
                deque.pollLast();

            deque.offerLast(i);

            if(i >= k - 1)
                res[i - k + 1] = nums[deque.peekFirst()]; 
        }

        return res;
    }
}