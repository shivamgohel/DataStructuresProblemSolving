import java.util.*;

class TopKFrequentElements
{
    public static void main(String[] args) 
    {
        /*
        Problem Statement:
        Given an array nums and an integer k, return the k most frequent elements.
        */

        int[] nums = {3,3,1,2,2,2,1,1,1,1};
        int k = 2;

        // Brute force approach (Sorting based)
        System.out.println(Arrays.toString(topKFrequentElementsBrute(nums,k))); 

        // Better approach using Min-Heap (Priority Queue)
        System.out.println(Arrays.toString(topKFrequentElementsBetter(nums,k))); 

        // Optimized approach using Bucket Sort
        System.out.println(Arrays.toString(topKFrequentElementsOptimized(nums,k)));      


    }

    /*
     * Approach 1: Brute Force (Sorting based)
     * Time Complexity: O(n log n) -> Sorting takes O(n log n)
     * Space Complexity: O(n) -> Stores elements in HashMap and List
     * - Count the frequency of each element using a HashMap.
     * - Sort elements based on frequency in descending order.
     * - Return the top k elements.
    */
    private static int[] topKFrequentElementsBrute(int[] nums, int k) {
        HashMap<Integer,Integer> freqMap = new HashMap<>();
        for(int num : nums)
            freqMap.put(num , freqMap.getOrDefault(num,0) +1);

        List<Integer> sortedKeys = new ArrayList<>(freqMap.keySet());
        sortedKeys.sort((a,b) -> freqMap.get(b) - freqMap.get(a));

        int[] res = new int[k];
        for(int i=0;i<k;i++)
            res[i] = sortedKeys.get(i);

        return res;         
    }
    
    /*
     * Approach 2: Min-Heap (Priority Queue)
     * Time Complexity: O(n log k) -> Each insert/remove operation in heap takes log k time.
     * Space Complexity: O(n) -> HashMap stores frequency, heap stores k elements.
     * - Count the frequency of each element using a HashMap.
     * - Use a Min-Heap to keep track of the top k frequent elements.
     * - Remove the least frequent element when heap size exceeds k.
    */
    private static int[] topKFrequentElementsBetter(int[] nums, int k) {
        HashMap<Integer,Integer> freqMap = new HashMap<>();
        for(int num : nums)
            freqMap.put(num , freqMap.getOrDefault(num, 0) +1);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a,b) -> freqMap.get(a) - freqMap.get(b)
        );

        for(int num : freqMap.keySet())
        {
            minHeap.add(num);
            if(minHeap.size() > k)
                minHeap.poll(); 
        }

        int[] res = new int[k];
        for(int i=k-1;i>=0;i--)
            res[i] = minHeap.poll();

        return res;    
    }
    
    /*
     * Approach 3: Bucket Sort
     * Time Complexity: O(n) -> Each element is processed in linear time.
     * Space Complexity: O(n) -> Uses extra space for buckets.
     * - Count the frequency of each element using a HashMap.
     * - Use an array of lists (buckets) where the index represents the frequency.
     * - Collect elements from the highest frequency bucket to get the top k elements.
    */
    private static int[] topKFrequentElementsOptimized(int[] nums, int k) {
        HashMap<Integer,Integer> freqMap = new HashMap<>();
        for(int num : nums)
            freqMap.put(num , freqMap.getOrDefault(num, 0) +1);

        List<Integer>[] buckets = new List[nums.length+1];
        for(int i=0;i<=nums.length;i++)
            buckets[i] = new ArrayList<>();

        for(int key : freqMap.keySet())
        {
            int freq = freqMap.get(key);
            buckets[freq].add(key);
        }

        List<Integer> resList = new ArrayList<>();
        for(int i=nums.length; i>=0 && resList.size() < k; i--)
            resList.addAll(buckets[i]);

        int[] res = new int[k];
        for(int i=0;i<k;i++)
            res[i] = resList.get(i);

        return res;                      
    }
}