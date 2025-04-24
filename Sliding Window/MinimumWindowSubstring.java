// https://leetcode.com/problems/minimum-window-substring/description/
import java.util.*;

class MinimumWindowSubstring
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         Given two strings `s` and `t`, return the minimum window substring of `s` that contains all characters in `t`.
         If no such substring exists, return an empty string.
        */

        String s = "ADOBECODEBANC";
        String t = "ABC";

        // Brute-force approach: O(n^2) time, O(k) space
        System.out.println(minimumWindowSubstringBrute(s,t));    

        // Optimized approach (Sliding Window with HashMap): O(n) time, O(k) space
        System.out.println(minimumWindowSubstringOptimized(s,t));    

    }

    /*
     Brute-force Approach:
     - The brute-force approach checks all possible substrings in `s` and determines if each contains all characters of `t`.
     - For each window (substring), we compare the character frequencies with those in `t`.
     - We maintain the smallest valid window by comparing the length of each valid window to the current smallest one.

     Approach Steps:
     1. Iterate through all possible starting points of a substring in `s` using the index `i`.
     2. For each starting point, iterate through all possible ending points `j` to create substrings.
     3. For each substring, count the characters and compare with the target string `t`.
     4. If the substring contains all characters from `t` with equal or higher frequency, check if it is the smallest valid window found so far.
     5. Return the smallest valid window found.

     Time Complexity: O(n^2) — two nested loops to check each window.
     Space Complexity: O(k) — k is the number of unique characters in the substring.
    */
    private static String minimumWindowSubstringBrute(String s, String t){
        if(s.length() < t.length())
            return "";

        String res = "";
        int minLength = Integer.MAX_VALUE;

        Map<Character,Integer> targetMap = new HashMap<>();
        for(char c : t.toCharArray())
            targetMap.put(c, targetMap.getOrDefault(c, 0) +1);

        for(int i=0;i<s.length();i++)
        {
            for(int j=i+1;j<=s.length();j++)
            {
                String sub = s.substring(i,j);
                
                Map<Character,Integer> windowMap = new HashMap<>();
                for(char c : sub.toCharArray())
                    windowMap.put(c, windowMap.getOrDefault(c, 0) +1);

                boolean isValid = true;

                for(char c : t.toCharArray())
                {
                    if(targetMap.get(c) > windowMap.getOrDefault(c, 0))
                    {
                        isValid = false;
                        break;
                    }
                }

                if(isValid && sub.length() < minLength)
                {
                    minLength = sub.length();
                    res = sub;
                }    
            }
        }  

        return res;      
    }

    /*
     Optimized Approach: Sliding Window with HashMap
     - The optimized approach uses two pointers (`left` and `right`) to maintain a sliding window that grows and shrinks dynamically.
     - The window expands by moving the `right` pointer and contracts by moving the `left` pointer to ensure we always have a valid window.
     - The algorithm uses a HashMap to track character counts for both the target string `t` and the current window in `s`.
     - The goal is to expand the window until it contains all characters from `t` and then minimize the window size by moving `left`.

     Approach Steps:
     1. Use two pointers `left` and `right` to represent the current window in string `s`.
     2. Expand the window by incrementing `right` and adding characters to the window map.
     3. When all characters in `t` are present in the window (i.e., the `windowMap` contains all required characters from `t`), try contracting the window by incrementing `left` to minimize its size.
     4. Keep track of the smallest valid window found and return it once the right pointer reaches the end of the string.

     Time Complexity: O(n) — The sliding window expands and contracts once per character, so the total work done is linear in terms of the length of the string.
     Space Complexity: O(k) — The space used is proportional to the number of unique characters in the target string `t` (k is the number of unique characters in `t`).
    */
    private static String minimumWindowSubstringOptimized(String s, String t) {
        if(s.length() < t.length())
            return "";
  
        int left = 0;
        int right = 0;
        int minLength = Integer.MAX_VALUE;
        int minStart = 0;

        Map<Character,Integer> targetMap = new HashMap<>();
        for(char c : t.toCharArray())
            targetMap.put(c, targetMap.getOrDefault(c, 0) +1);
        
        Map<Character,Integer> windowMap = new HashMap<>();

        int required = targetMap.size();
        int formed = 0;

        while(right < s.length())
        {
            char rightChar = s.charAt(right);
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) +1);

            if(targetMap.containsKey(rightChar) && 
               targetMap.get(rightChar).intValue() == windowMap.get(rightChar).intValue())
                formed++;

            while(formed == required)
            {
                if(right - left + 1 < minLength)
                {
                    minLength = right - left + 1;
                    minStart = left;
                }
            

                char leftChar = s.charAt(left);
                windowMap.put(leftChar, windowMap.getOrDefault(leftChar, 0) -1);

                if(targetMap.containsKey(leftChar) && 
                   targetMap.get(leftChar).intValue() > windowMap.get(leftChar).intValue())
                    formed--;

                left++; 
            } 

            right++;  
        }    

        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart , minStart+minLength);  
    }
}