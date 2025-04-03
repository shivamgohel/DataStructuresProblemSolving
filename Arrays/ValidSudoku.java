// https://leetcode.com/problems/valid-sudoku/description/
import java.util.*;

class ValidSudoku
{
    public static void main(String[] args) 
    {

        /*
         * Problem Statement:
         * Given a partially filled 9x9 Sudoku board, validate whether the board is valid according to the rules of Sudoku.
         * A valid Sudoku board is defined as follows:
         * 1. Each of the 9 rows must contain the digits 1-9 without repetition.
         * 2. Each of the 9 columns must contain the digits 1-9 without repetition.
         * 3. Each of the 9 sub-grids (3x3) must contain the digits 1-9 without repetition.
         * 
         * The board may be partially filled, where empty cells are represented by '.'.
         * Return true if the board is valid, otherwise return false.
        */
        
        char[][] board =    {{'1','2','.','.','3','.','.','.','.'},
                             {'4','.','.','5','.','.','.','.','.'},
                             {'.','9','8','.','.','.','.','.','3'},
                             {'5','.','.','.','6','.','.','.','4'},
                             {'.','.','.','8','.','3','.','.','5'},
                             {'7','.','.','.','2','.','.','.','6'},
                             {'.','.','.','.','.','.','2','.','.'},
                             {'.','.','.','4','1','9','.','.','8'},
                             {'.','.','.','.','8','.','.','7','9'}};

        
        // Brute-force approach: Checking rows, columns, and 3x3 sub-grids for duplicates
        System.out.println(validSudokuBrute(board));  

        // Optimized approach: Using bit manipulation to track seen numbers (bitmasking)
        System.out.println(validSudokuOptimized(board));  


    }

    /*
     * Brute-force approach:
     * - Check each row, each column, and each 3x3 sub-grid to ensure there are no duplicates.
     * - We use a HashSet to track numbers we've already encountered in the current row, column, or sub-grid.
     *   The reason we use a Set is because:
     *   1. Sets automatically handle duplicate values, so if a number is encountered again in a row, column, or sub-grid,
     *      we can immediately return false.
     *   2. The Set provides O(1) average time complexity for both insertions and lookups, making it an efficient choice for this task.
     * 
     * Time Complexity: O(n^2) - We iterate through each row, column, and sub-grid, so it's 9x9 iterations.
     * Space Complexity: O(n^2) - We use sets to track seen numbers, and each set can hold at most 9 elements (one for each row, column, and sub-grid).
    */
    private static boolean validSudokuBrute(char[][] board){
        // check for rows
        for(int row=0;row<9;row++)
        {
            Set<Character> seen = new HashSet<>();
            for(int i=0;i<9;i++)
            {
                if(board[row][i] == '.') 
                    continue;
                if(seen.contains(board[row][i])) 
                    return false;
                seen.add(board[row][i]);
            }
        }

        // check for columns
        for(int col=0;col<9;col++)
        {   
            Set<Character> seen = new HashSet<>();
            for(int i=0;i<9;i++)
            {   
                if(board[i][col] == '.')
                    continue;
                if(seen.contains(board[i][col]))
                    return false;    
                seen.add(board[i][col]);
            }
        }

        // check for 3*3 squares
        for(int square=0;square<9;square++)
        {
            Set<Character> seen = new HashSet<>();
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    int row = (square/3) * 3 + i;
                    int col = (square%3) * 3 + j;
                    
                    if(board[row][col] == '.')
                        continue;

                    if(seen.contains(board[row][col]))
                        return false;
                            
                    seen.add(board[row][col]);
                }
            }
        }

        return true;
    }

    /*
     * Optimized approach using bit manipulation:
     * - Use bitmasks to track which numbers have been seen in rows, columns, and 3x3 sub-grids.
     * The bitmask works by using a single integer (int) to represent which digits are encountered.
     * For example, if '1' is seen, we set the first bit (bit 0) to 1, if '2' is seen, we set the second bit (bit 1) to 1, and so on.
     * Time Complexity: O(n^2) - We still iterate through each cell in the 9x9 grid, so it's 9x9 iterations.
     * Space Complexity: O(n) - We use fixed-size integer arrays to track seen digits, so only constant space is used.
     * Approach: Bit manipulation allows us to efficiently track the occurrence of numbers in rows, columns, and sub-grids.
    */
    private static boolean validSudokuOptimized(char[][] board) {
        int[] rows = new int[9];           // Tracks numbers seen in rows
        int[] cols = new int[9];           // Tracks numbers seen in columns
        int[] squares = new int[9];        // Tracks numbers seen in 3x3 sub-grids

        for(int r=0;r<9;r++)
        {
            for(int c=0;c<9;c++)
            {
                if(board[r][c] == '.')
                    continue;

                int val = board[r][c] - '1';    // Convert the character to a number between 0-8

                // Check if the number has already appeared in the same row, column, or sub-grid
                // Using bitwise AND to check if the corresponding bit is already set.
                if((rows[r] & (1 << val)) > 0 || 
                   (cols[c] & (1 << val)) > 0 || 
                   (squares[(r/3) * 3 + (c/3)] & (1 << val)) > 0)
                   return false;

                // Set the corresponding bit for the number in the respective row, column, and sub-grid
                // Using bitwise OR to set the corresponding bit.
                rows[r] |= (1 << val); 
                cols[c] |= (1 << val);
                squares[(r/3) * 3 + (c/3)] |= (1 << val);   
            }
        }

        return true;
    }

}