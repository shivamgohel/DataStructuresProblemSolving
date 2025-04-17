// https://leetcode.com/problems/permutation-in-string/description/
import java.util.*;

class PermutationInString
{
    public static void main(String[] args) 
    {   
        /*
         Problem Statement:
         Given two strings `s1` and `s2`, write a function to determine if `s2` contains a permutation of `s1`.
         In other words, check if any of the permutations of `s1` exist as a substring in `s2`.
         Return `true` if such a permutation exists, otherwise return `false`.
        */

        String s1 = "ab"; // -> consider length m
        String s2 = "eidbaooo"; // -> consider length n

        // Brute-force approach: O(n * m log m) time, O(m) space
        System.out.println(permutationInStringBrute(s1,s2));

        // Better approach: O(n * m) time, O(m) space
        System.out.println(permutationInStringBetter(s1,s2));

        // Optimized approach: O(n) time, O(1) space (since fixed size of character set)
        System.out.println(permutationInStringOptimized(s1,s2));


    }

    /*
     Brute-force Approach:
     - For each window of size `s1.length()` in `s2`, check if it is a permutation of `s1`.
     - Sort both `s1` and the current window of `s2` and compare the sorted versions.
     - If any window matches the sorted `s1`, return true.

     Time Complexity: O(n * m log m), where n is the length of s2, and m is the length of s1. Sorting each window takes O(m log m).
     Space Complexity: O(m), for storing the sorted strings.
    */
    private static boolean permutationInStringBrute(String s1, String s2) {
        if(s2.length() < s1.length())
            return false;

        char[] s1CharArray = s1.toCharArray();
        Arrays.sort(s1CharArray);
        String s1SortedString = new String(s1CharArray);

        for(int i=0;i<=s2.length() - s1.length();i++)
        {
            String s2WindowString = s2.substring(i, i + s1.length());
            char[] s2WindowCharArray = s2WindowString.toCharArray();
            Arrays.sort(s2WindowCharArray);
            String s2SortedString = new String(s2WindowCharArray);

            if(s1SortedString.equals(s2SortedString))
                return true;
        }    

        return false;
    }

    /*
     Better Approach:
     - Use a HashMap to count the frequency of characters in `s1`.
     - For each window of size `s1.length()` in `s2`, count the characters in the window and compare it with `s1`'s character counts.
     - If they match, it’s a permutation of `s1`.

     **Why HashMap over Sorting?**
     - Sorting would take O(m log m) time where `m` is the length of `s1`, and we'd need to sort every window of size `m` in `s2`, which would be inefficient.
     - Using a HashMap allows us to count characters in linear time, making this approach more efficient.
     - Instead of sorting, we just compare the frequencies of characters, which can be done in O(m) time for each window.

     Time Complexity: O(n * m), where n is the length of s2, and m is the length of s1. Counting characters in each window takes O(m).
     Space Complexity: O(m), for storing the character frequencies in HashMaps.
    */
    private static boolean permutationInStringBetter(String s1, String s2) {
        if(s2.length() < s1.length())
            return false;

        HashMap<Character,Integer> s1Map = new HashMap<>();
        for(char ch : s1.toCharArray())
            s1Map.put(ch, s1Map.getOrDefault(ch, 0) +1);

        for(int i=0;i<=s2.length()-s1.length();i++)
        {
            HashMap<Character,Integer> s2WindowMap = new HashMap<>();
            String s2WindowString = s2.substring(i , i + s1.length());
            for(char ch : s2WindowString.toCharArray())
                s2WindowMap.put(ch, s2WindowMap.getOrDefault(ch, 0) +1);

            if(s1Map.equals(s2WindowMap))
                return true;    
        }

        return false;        
    }

    /*
     Optimized Approach:
     - Use two frequency arrays of size 26: one for `s1` and one for the current window in `s2`.
     - Initially populate the window array for the first `m` characters of `s2` where `m` is the length of `s1`.
     - Slide the window across `s2` by adding the character that enters the window and removing the character that exits.
     - After updating the window, compare the frequency arrays of `s1` and the current window.
     - If they match, return true as it indicates a permutation of `s1` exists in `s2`.
     - This approach reduces the time complexity to O(n) as we only update the arrays for each character entering or leaving the window.
     - Space complexity remains O(1) due to the fixed-size frequency arrays.
    
     Time Complexity: O(n), where n is the length of `s2`. Sliding the window and updating frequencies takes constant time per character.
     Space Complexity: O(1), because we are using fixed-size arrays (size 26 for lowercase letters).
    */
    private static boolean permutationInStringOptimized(String s1, String s2) {
        if(s2.length() < s1.length())
            return false;

        int[] s1Freq = new int[26];
        int[] s2WindowFreq = new int[26];

        for(int i=0;i<s1.length();i++)
        {
            s1Freq[s1.charAt(i) - 'a']++;
            s2WindowFreq[s2.charAt(i) - 'a']++;
        }     

        if(Arrays.equals(s1Freq, s2WindowFreq))
            return true;

        for(int i=s1.length();i<s2.length();i++)
        {
            s2WindowFreq[s2.charAt(i) - 'a']++;

            s2WindowFreq[s2.charAt(i - s1.length()) - 'a']--;

            if(Arrays.equals(s1Freq, s2WindowFreq))
                return true;
        }

        return false;
    }
}