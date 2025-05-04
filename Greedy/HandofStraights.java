// https://leetcode.com/problems/hand-of-straights/description/
import java.util.*;

class HandofStraights
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         You are given an array `hand` of integers representing a hand of cards, and an integer `groupSize`. 
         The task is to determine if it is possible to rearrange the cards into groups of `groupSize` such that 
         each group contains `groupSize` consecutive cards.
         Return `true` if you can rearrange the hand into groups of `groupSize`, otherwise return `false`.
        */

        int[] hand = {5,1,0,6,4,5,3,0,8,9};
        int groupSize = 2;

        // Brute-force approach: O(n^2) time, O(n) space
        System.out.println(handOfStraightsBrute(hand, groupSize));

        // Optimized approach 1: O(n log n) time, O(n) space
        System.out.println(handOfStraightsOptimized1(hand, groupSize));

        // Optimized approach 2: O(n log n) time, O(n) space
        System.out.println(handOfStraightsOptimized2(hand, groupSize));


    }
    
    /*
    Brute-force approach:
    - First, we check if the length of the hand is divisible by the group size. 
      If not, return false.
    - We sort the hand to ensure that we are starting from the smallest element 
      in order to form the smallest consecutive group possible.
    - Starting from the smallest card, we attempt to form a group by incrementing 
      the current card by 1 for each subsequent card in the group.
    - We repeatedly try to form groups from the sorted hand by checking if the 
      next consecutive cards exist in the list.
    - The loop `i = 0 to i < groupSize` ensures we are forming a valid sequence 
      by sequentially checking if the next `groupSize` consecutive cards are present.
    - If we can form the groups successfully by continuously removing used cards, 
      return true.
    - Otherwise, return false.

    Why start from the minimum element?
    - Starting from the minimum element ensures that we always attempt to form 
      groups from the smallest possible consecutive sequence.
    - If we start from a larger element without ensuring that smaller groups are 
      already formed, we may miss forming valid groups, especially if cards are 
      scattered or there are gaps.

    Time Complexity: O(n^2), due to checking for the existence of each element 
    in the list. The `contains` method takes O(n) time, leading to an overall 
    O(n^2) time complexity.
    Space Complexity: O(n) for storing the sorted hand.

    Drawback: Inefficient, especially with larger arrays, because of the repeated 
    search for each card in the hand.
    */
    private static boolean handOfStraightsBrute(int[] hand, int groupSize) {
        if(hand.length % groupSize != 0)
            return false;

        ArrayList<Integer> handList = new ArrayList<>();
        for(int nums : hand)
            handList.add(nums);
        
        Collections.sort(handList);

        while(!handList.isEmpty())
        {
            int first = handList.get(0);
            for(int i=0; i<groupSize; i++)
            {
                int target = first + i;
                if(!handList.contains(target))
                    return false;

                handList.remove((Integer) target);    
            }
        }

        return true;                   
    }

    /*
    Optimized approach 1:
    - We use a **HashMap** to count the occurrences of each card in the hand. 
      This allows us to efficiently check if the necessary cards are available 
      to form groups, without needing to search the list repeatedly as in the 
      brute-force approach.
    - We start with the smallest card and attempt to form consecutive groups 
      by checking for the next `groupSize` consecutive cards.
    - The **HashMap** helps in efficiently tracking the remaining occurrences 
      of each card, so we can ensure that a valid group can be formed without 
      needing to remove cards from a list or use `contains()` repeatedly.
    - This approach is more efficient than the brute-force approach because it 
      avoids repeatedly searching the list and instead works with direct lookups 
      and updates in the HashMap.

    Why use a **HashMap** instead of a normal array?
    - A **HashMap** is chosen because it allows for fast lookups and updates. 
      Specifically, it provides average constant-time complexity (O(1)) for checking 
      if a card exists and updating its count. This is much faster than using an 
      array with a linear search or a list with `contains()`, which would take O(n) time.
    - With a normal array, we would need to check each element individually to 
      see if a card exists or update the count, which could be slower, especially 
      for larger inputs. The HashMap allows us to directly access the count of any 
      card without iterating over the entire hand.
    - **Note**: In this approach, we do not remove cards from the **HashMap** 
      after using them to form a group. Instead, we decrement the count of each card. 
      This ensures that the frequency of each card is maintained, allowing us to reuse 
      the same card if needed for future groups.

    Time Complexity: O(n log n) due to sorting the hand, O(n) for counting card occurrences, 
    and O(n) for iterating through the hand to form groups. The sorting dominates, 
    leading to an overall time complexity of O(n log n).
    Space Complexity: O(n) for storing the map of card counts.

    Drawback: Still requires sorting the hand, which can be time-consuming for very 
    large inputs. The HashMap provides better performance but does not fully eliminate 
    the need for sorting. Additionally, since we do not remove cards from the **HashMap**, 
    the logic relies entirely on updating the frequency of the cards.
    */
    private static boolean handOfStraightsOptimized1(int[] hand, int groupSize) {
        if(hand.length % groupSize != 0)
            return false;

        Arrays.sort(hand);

        HashMap<Integer,Integer> handMap = new HashMap<>();
        for(int nums : hand)
            handMap.put(nums ,handMap.getOrDefault(nums, 0) +1);

        for(int start : hand)
        {
            if(handMap.getOrDefault(start, 0)  == 0)
                continue;

            for(int i=0; i<groupSize; i++)
            {
                int target = start + i;
                if(handMap.getOrDefault(target, 0) == 0)
                    return false;

                handMap.put(target, handMap.get(target) -1);    
            }    
        }

        return true;        
    }
    
    /*
    Optimized approach 2:
    - We use a **TreeMap** to maintain a sorted list of cards. 
      The **TreeMap** automatically keeps the cards in ascending order based on 
      their natural ordering (i.e., their values). This eliminates the need for 
      an explicit sorting step, as the TreeMap maintains the order at all times.
    - The **TreeMap** allows us to efficiently pick the smallest available card 
      at any point, which is crucial for forming consecutive groups.
    - As with the previous approach, after using a card to form a group, we 
      decrement its count in the map. If the count of a card reaches zero, 
      we remove it from the **TreeMap**. This ensures that we only attempt to 
      form groups with cards that are still available, keeping the map clean 
      and reducing unnecessary lookups.

    Why use a **TreeMap** instead of a **HashMap**?
    - **Sorted Order**: A **TreeMap** automatically maintains the cards in sorted 
      order. This means that we can always get the smallest card by simply 
      calling the first entry (`firstEntry()`), whereas with a **HashMap**, 
      we would need to explicitly sort the cards first. This simplifies the logic 
      and avoids the need for an explicit sorting step.
    - **Efficient Operations**: The **TreeMap** offers **logarithmic time complexity (O(log n))** 
      for insertion, deletion, and search operations. This is slightly slower than 
      the **O(1)** complexity of **HashMap** for lookups, but the tradeoff is that the **TreeMap** 
      guarantees that the elements are always sorted. For this specific problem, having access 
      to the smallest card without needing to sort is an advantage, as it allows us to form groups efficiently.
    - **Removing Cards**: With a **TreeMap**, we can remove entries when their count reaches zero. 
      This is an efficient operation that ensures we don't have unnecessary elements lingering in 
      the map. In a **HashMap**, we could also decrement counts, but the **TreeMap** gives us the 
      ability to quickly manage the sorted nature of the cards without needing an extra data structure 
      or sorting operation. When the frequency of a card hits zero, we clean it up by removing it from 
      the map, reducing memory usage and improving lookup times.

    Time Complexity:
    - **O(n log n)** due to the **TreeMap** operations for insertion, deletion, and fetching 
      the smallest available card.
    - The **log n** complexity for the operations is derived from the balanced tree structure of the **TreeMap**.
    - Sorting is not needed here as the **TreeMap** handles it implicitly.

    Space Complexity: **O(n)** for storing the **TreeMap** with card counts.

    Drawback: While the **TreeMap** improves efficiency by maintaining sorted order, 
    it still has a **log n** complexity for the insertion and removal operations, 
    which is slightly slower than the **O(1)** operations available in a **HashMap**. 
    Additionally, the **TreeMap** still requires a form of sorting, though it's managed 
    automatically, which means the efficiency gain is marginal when compared to the **HashMap** 
    approach. However, the **TreeMap** simplifies the task by handling sorting implicitly 
    and allows efficient removal of cards once their frequency reaches zero, cleaning up the map automatically.
    */
    private static boolean handOfStraightsOptimized2(int[] hand, int groupSize) {
        if(hand.length % groupSize != 0)
            return false;

        TreeMap<Integer, Integer> handTreeMap = new TreeMap<>();
        for(int nums : hand)
            handTreeMap.put(nums, handTreeMap.getOrDefault(nums, 0) +1);

        while(!handTreeMap.isEmpty())
        {
            int start = handTreeMap.firstKey();
            for(int i=0; i<groupSize; i++)
            {
                int target = start + i;
                if(!handTreeMap.containsKey(target))
                    return false;

                handTreeMap.put(target, handTreeMap.get(target) -1);   
                if(handTreeMap.get(target) == 0)
                    handTreeMap.remove(target); 
            }
        }        

        return true;
    }
}