// https://leetcode.com/problems/group-anagrams/description/
import java.util.*;

class GroupAnagrams
{
    public static void main(String[] args) 
    {   
        /*
        Problem Statement:
        Given an array of strings, group anagrams together.
        */

        String[] strs = {"act","pots","tops","cat","stop","hat"};

        // Brute force approach (Sorting as key)
        System.out.println(groupAnagramsBrute(strs)); 

        // Optimized approach (Character frequency count)
        System.out.println(groupAnagramsOptimized(strs));
    }

    /*
     * Approach 1: Brute Force (Sorting)
     * Time Complexity: O(N * K log K) -> Sorting each word takes O(K log K), and we do it for N words.
     * Space Complexity: O(NK) -> HashMap stores all words with their keys.
     * - Convert each word into a character array and sort it.
     * - Use the sorted version as a key in a HashMap to group anagrams.
    */
    private static List<List<String>> groupAnagramsBrute(String[] strs) {
        HashMap<String, List<String>> res = new HashMap<>();

        for(String s : strs)
        {   
            // Convert the string into a character array and sort it
            char[] charArray = s.toCharArray();
            Arrays.sort(charArray);

            // Convert sorted char array back into a string (sorted representation as key)
            String newS = Arrays.toString(charArray);

            // If key doesn't exist, create a new list
            res.putIfAbsent(newS, new ArrayList<>());
            // Add the current string to its corresponding anagram group
            res.get(newS).add(s);
        }

        return new ArrayList<>(res.values());
    
        /*
        Final HashMap Content:
            {
                "act" -> ["act", "cat"],  // "act" and "cat" are anagrams
                "opst" -> ["pots", "tops", "stop"],  // "pots", "tops", and "stop" are anagrams
                "aht" -> ["hat"]  // "hat" has no anagram
            }
        */
    }


    /*
     * Approach 2: Optimized (Character Frequency Count)
     * Time Complexity: O(NK) -> Counting characters takes O(K), and we do it for N words.
     * Space Complexity: O(NK) -> HashMap stores all words with their frequency keys.
     * - Create an array of size 26 to count occurrences of each character.
     * - Use this frequency array as a unique key.
     * - Store words with the same frequency key in a HashMap.
    */
    private static List<List<String>> groupAnagramsOptimized(String[] strs) {
        HashMap<String , List<String>> res = new HashMap<>();

        for(String s : strs)
        {   
            // Frequency array to count occurrences of each character
            int[] freq = new int[26];
            for(char c : s.toCharArray())
            {
                freq[c - 'a']++;
            }

            String key = Arrays.toString(freq);
            res.putIfAbsent(key, new ArrayList<>());
            res.get(key).add(s);
        }

        return new ArrayList<>(res.values());

    /*
    Final HashMap Content:
    {
        "[1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]" 
            -> ["act", "cat"],  // "act" and "cat" are anagrams

        "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0]" 
            -> ["pots", "tops", "stop"],  // "pots", "tops", and "stop" are anagrams

        "[0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0]" 
            -> ["hat"]  // "hat" has no anagram
    }
    */

    }

}