import java.util.*;

class EncodeDecodeStrings
{
    public static void main(String[] args) 
    {   
        /*
        Problem Statement:
        Design an algorithm to encode a list of strings into a single string 
        and decode it back to the original list.

        Algorithm:
        1. **Encoding:**
           - For each string in the list:
             - Append its length.
             - Append a '#' separator.
             - Append the string itself.
           - Example: ["leet", "codes"] → "4#leet5#codes"

        2. **Decoding:**
           - Start from the beginning of the encoded string.
           - Find the next '#' which separates the length from the actual string.
           - Extract the number before '#' (this represents the length of the string).
           - Extract the substring of that length.
           - Move the pointer forward and repeat until the end of the string.
           - Example: "4#leet5#codes" → ["leet", "codes"]
        */

        List<String> originalString = Arrays.asList("leet", "codes");
        String encodedString = encode(originalString);

        System.out.println(encode(originalString));
        System.out.println(decode(encodedString)); 

    }

    /*
     * Approach: Encoding Strings
     * Time Complexity: O(n) -> Processes each character once.
     * Space Complexity: O(n) -> Stores the encoded string.
     * - Each string is prefixed with its length and a '#' separator.
     * - This ensures that even if a string contains '#', it can still be decoded correctly.
    */
    private static String encode(List<String> strs){
        StringBuilder encodedString = new StringBuilder();
        for(String s : strs)
        {
            encodedString.append(s.length()).append('#').append(s);
        }
        
        return encodedString.toString();
    }

    /*
     * Approach: Decoding Strings
     * Time Complexity: O(n) -> Iterates through each character of the encoded string.
     * Space Complexity: O(n) -> Stores the decoded list of strings.
     * - Iterates through the encoded string to extract lengths and substrings.
     * - Uses `#` as a delimiter to separate the length from the actual string.
    */
    private static List<String> decode(String strs){
        List<String> decodedString = new ArrayList<>();
        int i = 0;
        while(i < strs.length())
        {
            int j = strs.indexOf('#' , i);
            int length = Integer.parseInt(strs.substring(i,j));
            i = j + 1;
            decodedString.add(strs.substring(i,i+length));
            i += length;
        }

        return decodedString;
    }
}