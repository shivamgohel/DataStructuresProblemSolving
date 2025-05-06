// https://leetcode.com/problems/partition-labels/description/
import java.util.*;

class PartitionLabels
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         Given a string `s`, partition it into as many parts as possible so that each letter appears in at most one part.
         Return a list of integers representing the size of these parts.

         Example:
         Input: s = "ababcbacadefegdehijhklij"
         Output: [9,7,8]
         Explanation:
         - Partition 1: "ababcbaca" (a, b, c not repeated later)
         - Partition 2: "defegde" (d, e, f not repeated later)
         - Partition 3: "hijhklij" (h, i, j, k, l not repeated later)
        */
        
        String s = "abcabc";
        
        // Optimized approach using HashMap: O(n) time, O(1) space
        System.out.println(partitionLabelsOptimized1(s)); 

        // Optimized approach using array: O(n) time, O(1) space
        System.out.println(partitionLabelsOptimized2(s));     

    
    }     
    
    /*
     Optimized Approach 1: Using HashMap to store the last index of each character

     Idea:
     - First, map each character to its last occurrence in the string.
     - Then iterate through the string while keeping track of the farthest end of the current partition.
     - When current index `i` equals `end`, we know we’ve reached the end of the current partition.
     - Store the partition size and reset `start`.

     Time Complexity: O(n) — where n is the length of the string
     Space Complexity: O(1) — The map has at most 26 entries (for lowercase a-z)
    */
    private static List<Integer> partitionLabelsOptimized1(String s) {
        Map<Character, Integer> indexMap = new HashMap<>();
        for(int i=0; i<s.length(); i++)
            indexMap.put(s.charAt(i), i);   

        List<Integer> res = new ArrayList<>();
        int start = 0;
        int end = 0;

        for(int i=0;i<s.length();i++)
        {
            end = Math.max(end, indexMap.get(s.charAt(i)));

            if(i == end)
            {
                res.add(end - start + 1);
                start = i + 1;
            }
        }

        return res;    
    }

    /*
     Optimized Approach 2: Using an array to store the last index of each character

     Idea:
     - Instead of using a HashMap, we use an array of fixed size (26) to store the last index of each character.
     - The array index corresponds to a character, with the value at each index representing the last occurrence of that character in the string.
     - Then, we iterate through the string while keeping track of the farthest end of the current partition.
     - When current index `i` equals `end`, we know we’ve reached the end of the current partition.
     - Store the partition size and reset `start`.

     Time Complexity: O(n) — where n is the length of the string
     Space Complexity: O(1) — The array has at most 26 entries (for lowercase a-z)
    */
    private static List<Integer> partitionLabelsOptimized2(String s) {
        int[] indexArray = new int[26];
        for(int i=0; i<s.length(); i++)
            indexArray[s.charAt(i) - 'a'] = i;

        List<Integer> res = new ArrayList<>();
        int start = 0;
        int end = 0;

        for(int i=0; i<s.length(); i++)
        {
            end = Math.max(end, indexArray[s.charAt(i) - 'a']);

            if(i == end)
            {
                res.add(end - start + 1);
                start = i + 1;
            }
        }    

        return res;
    }
}