import java.util.*;

class Valid_Anagram
{
    public static void main(String[] args) 
    {   
        /*
        Problem Statement:
        Given two strings s and t, check if t is an anagram of s.

        An anagram means both strings contain the same characters with the same frequency, but the order does not matter.
        */

        String s = "abcde";
        String t = "edcba";  

        // Brute-force approach: Sorting both strings and comparing them
        System.out.println(validAnagramBrute(s,t));   

        // HashMap-based approach: Counting frequency of characters
        System.out.println(validAnagramOptimized1(s, t));

        // Frequency array approach: Using an array for character counts
        System.out.println(validAnagramOptimized2(s, t));
    }
    
    /*
     * Brute-force approach: Sort both strings and compare
     * Time Complexity: O(n log n) - Due to sorting
     * Space Complexity: O(1) 
     * Approach: Sorting ensures that identical characters appear in the same order.
    */
    private static boolean validAnagramBrute(String s, String t){
        if(s.length() != t.length())
            return false;

        char[] arrayS = s.toCharArray();
        char[] arrayT = t.toCharArray();

        Arrays.sort(arrayS);
        Arrays.sort(arrayT);

        return Arrays.equals(arrayS, arrayT);   
    }

    /*
     * HashMap-based approach: Count frequency of characters in both strings
     * Time Complexity: O(n) - Single pass through strings
     * Space Complexity: O(n) - HashMap storage
     * Drawback: Two HashMaps are used, which can be optimized to one.
    */
    private static boolean validAnagramOptimized1(String s, String t){
        if(s.length() != t.length())
            return false;

        HashMap<Character,Integer> hashS = new HashMap<>();
        HashMap<Character,Integer> hashT = new HashMap<>();

        for(int i=0;i<s.length();i++)
        {
            hashS.put(s.charAt(i), hashS.getOrDefault(s.charAt(i), 0) +1);
            hashT.put(t.charAt(i), hashT.getOrDefault(t.charAt(i), 0) +1);
        }

        return hashS.equals(hashT);    
    }

    /*
     * Frequency array approach: Using an integer array for character frequency
     * Time Complexity: O(n) - Single pass through strings
     * Space Complexity: O(1) - Only a fixed array of size 26 is used
     * Approach: Instead of a HashMap, an integer array (size 26) is used to store character frequencies.
     * This is possible because the problem states that only lowercase English letters are used.
    */
    private static boolean validAnagramOptimized2(String s, String t){
        if(s.length() != t.length())
            return false;

        int[] freqArray = new int[26];

        // Increase for 's' characters, decrease for 't' characters
        for(int i=0;i<s.length();i++)
        {
            freqArray[s.charAt(i) - 'a']++;
            freqArray[t.charAt(i) - 'a']--;
        }

        for(int x : freqArray)
        {
            if(x!=0)
                return false;
        }

        return true;    
    }
}