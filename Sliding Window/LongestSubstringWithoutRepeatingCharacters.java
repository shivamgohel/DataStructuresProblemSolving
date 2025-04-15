// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
import java.util.*;

class LongestSubstringWithoutRepeatingCharacters
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         Given a string `s`, find the length of the longest substring without repeating characters.
         Return the maximum length of such substring.
        */
        
        String s = "abcabcbb";
        
        // Brute-force approach: O(n^2) time, O(k) space
        System.out.println(longestSubstringWithoutRepeatingCharactersBrute(s));

        // Optimized approach 1 (Sliding Window using HashSet): O(n) time, O(k) space
        System.out.println(longestSubstringWithoutRepeatingCharactersOptimized1(s));

        // Optimized approach 2 (Sliding Window using HashMap): O(n) time, O(k) space
        System.out.println(longestSubstringWithoutRepeatingCharactersOptimized2(s));


    }

    /*
     Brute-force Approach:
     - Start from each character in the string.
     - Keep adding characters to a set until a duplicate character appears.
     - Calculate the length of this substring.
     - Keep track of the maximum length found.

     Time Complexity: O(n^2) — two nested loops.
     Space Complexity: O(k) — k is the number of unique characters in the substring.
    */
    private static int longestSubstringWithoutRepeatingCharactersBrute(String s) {
        int maximum_length = 0;
        
        for(int i=0;i<s.length();i++)
        {
            HashSet<Character> seen = new HashSet<>();
            for(int j=i;j<s.length();j++)
            {
                char current_character = s.charAt(j);
                if(seen.contains(current_character))
                    break;

                seen.add(current_character);
                maximum_length = Math.max(maximum_length, j - i + 1);    
            }
        }

        return maximum_length;
    }

    /*
     Optimized Approach 1: Sliding Window using HashSet
     - Use a sliding window [left, right] to keep track of the current substring with unique characters.
     - If we see a duplicate character at `right`, move `left` forward until the duplicate is removed from the window.
     - Keep expanding `right` and update the maximum length.

     Time Complexity: O(n)
     Space Complexity: O(k), k = number of unique characters in the window
    */
    private static int longestSubstringWithoutRepeatingCharactersOptimized1(String s) {
        int maximum_length = 0;
        HashSet<Character> seen = new HashSet<>();
        int left = 0;

        for(int right=0;right<s.length();right++)
        {
            while(seen.contains(s.charAt(right)))
            {
                seen.remove(s.charAt(left));
                left++;
            }

            seen.add(s.charAt(right));
            maximum_length = Math.max(maximum_length, right - left + 1);
        }

        return maximum_length;
    }

    /*
     Optimized Approach 2: Sliding Window using HashMap
     - Use a HashMap to store the last seen index of each character.
     - When a duplicate is found, jump the `left` pointer to the position after the last occurrence of that character.
     - This avoids checking/removing one-by-one like in the HashSet version.

     Time Complexity: O(n)
     Space Complexity: O(k), k = number of unique characters in the map
    */
    private static int longestSubstringWithoutRepeatingCharactersOptimized2(String s) {
        int maximum_length = 0;
        HashMap<Character,Integer> seen = new HashMap<>();
        int left = 0;

        for(int right=0;right<s.length();right++)
        {
            char current_character = s.charAt(right);
            if(seen.containsKey(current_character))
            {
                left = Math.max(left, seen.get(current_character) + 1);
            }

            seen.put(s.charAt(right), right);
            maximum_length = Math.max(maximum_length, right - left + 1);
        }

        return maximum_length;
    }
}