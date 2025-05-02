// https://leetcode.com/problems/gas-station/description/

class GasStation
{
    public static void main(String[] args) 
    {
        /*
         Problem Statement:
         There are `n` gas stations arranged in a circle. You are given two integer arrays `gas` and `cost` 
         where:
            - `gas[i]` is the amount of gas at station `i`,
            - `cost[i]` is the cost of gas to travel from station `i` to station `i + 1`.

         Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.
         If there exists a solution, it is guaranteed to be unique.
        */

        int[] gas = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};
        
        // Brute-force approach: O(n^2) time
        System.out.println(gasJumpBrute(gas,cost));    

        // Better approach: O(n) time, O(1) space, with explicit traversal tracking
        System.out.println(gasJumpBetter(gas,cost));   

        // Optimized approach: Kadane’s-like greedy traversal, O(n) time, O(1) space    
        System.out.println(gasJumpOptimized(gas,cost));    


    }

    /*
     Brute-force Approach:
     - Try starting from each station and simulate the complete circuit.
     - If you can complete the circuit without running out of gas, return that station.

     Approach Steps:
     1. Iterate through each station as starting point.
     2. For each start, simulate the full trip:
        - Keep a `tank` variable to track fuel.
        - If tank becomes negative, break and try next station.
     3. If any simulation completes successfully, return start index.

     Time Complexity: O(n^2) — for each start, up to `n` checks.
     Space Complexity: O(1) — only variables used.
    */
    private static int gasJumpBrute(int[] gas, int[] cost){
        int n = gas.length;

        for(int start=0; start<n; start++)
        {
            int tank = 0;
            boolean canTravel = true;
            for(int step=0; step<n; step++)
            {
                int stationIndex = (start + step) % n;

                tank += gas[stationIndex];
                tank -= cost[stationIndex];

                if(tank < 0)
                {
                    canTravel = false;
                    break;
                }
            }

            if(canTravel)
                return start;
        }

        return -1;
    }
    
    /*
     Better Approach:
     - First check if a solution is even possible: total gas must be >= total cost.
     - Then simulate traversal from a valid starting point.
     - Reset tank and start whenever you can't continue.

     Approach Steps:
     1. Compute `totalGas` and `totalCost` — if gas < cost, return -1.
     2. Use a circular traversal with two pointers:
        - `start` points to candidate station.
        - `end` advances around the circle.
     3. Track current fuel in `tank`, reset if it goes below 0.

     Time Complexity: O(n) — every station visited at most twice.
     Space Complexity: O(1)
    */
    private static int gasJumpBetter(int[] gas, int[] cost) {
        int n = gas.length;
        
        int totalGas = 0;
        int totalCost = 0;
        for(int i=0; i<n; i++)
        {
            totalGas += gas[i];
            totalCost += cost[i];
        }
        if(totalGas < totalCost)
            return -1;

        int start = 0;
        int end = 0;
        int tank = 0;
        int count = 0;
        while(count < n)
        {
            tank += gas[end];
            tank -= cost[end];

            if(tank < 0)
            {
                count = 0;
                tank = 0;
                start = (end+1) % n;
                end = start;
            }
            else if(tank >= 0)
            {
                end = (end+1) % n;
                count++;
            }
        }

        return start;       
    }

    /*
     Optimized Greedy Approach (Single Pass):
     Key Idea:
     - If the total amount of gas is less than the total cost, it's impossible to complete the circuit → return -1.
     - Otherwise, there must be at least one starting station from which we can complete the trip.

     Why It Works:
     - We track the fuel balance (`tank`) while moving from station to station.
     - If at any point the `tank` becomes negative, it means we can't reach the next station from the current starting point.
     - So, we reset the starting point to the next station (`start = i + 1`) and reset the tank.
     - This works because if a valid solution exists, it must lie beyond any segment where `tank` drops below 0.

     Why No Need for Modulo (% n):
     - Unlike the brute-force or circular sliding window approach, this method only checks **one linear traversal**.
     - Since we're guaranteed a solution exists (if totalGas ≥ totalCost), a full loop is unnecessary.
     - We only need to find the point where the "net gain"->(gas[i]-cost[i]), becomes consistently positive.

     Steps:
     1. Initialize `start = 0`, `tank = 0`, and sums for `totalGas` and `totalCost`.
     2. Traverse all stations from 0 to n-1:
        - Add the net gas gain: `tank += gas[i] - cost[i]`.
        - If `tank < 0`, reset `tank = 0` and move `start` to `i + 1`.
     3. After the loop, return `start` if `totalGas >= totalCost`, else return -1.

     Time Complexity: O(n) — single pass through the array.
     Space Complexity: O(1) — constant extra space.
    */
    private static int gasJumpOptimized(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int tank = 0;
        int start = 0;

        for(int i=0;i<gas.length;i++)
        {
            totalGas += gas[i];
            totalCost += cost[i];

            tank += (gas[i] - cost[i]);

            if(tank < 0)
            {
                tank = 0;
                start = i + 1;
            }
        }

        return totalGas >= totalCost ? start : -1;
    }
}