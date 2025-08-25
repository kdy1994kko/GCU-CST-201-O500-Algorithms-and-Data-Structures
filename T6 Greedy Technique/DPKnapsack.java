
// Dynamic Programming Algorithm
public class DPKnapsack {
    public static void main(String[] args) {
        // Item weights and values
        int[] weights = {20, 30, 40, 60, 70, 90};
        int[] values =  {70, 80, 90,110,120,200};
        int capacity = 280; // Max capacity of the knapsack
        int n = weights.length;

        // dp[i][w] represents max value using first i items with capacity w
        int[][] dp = new int[n + 1][capacity + 1];

        int steps = 0; // Count of steps (iterations) for complexity

        // Build DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                steps++; // Counting each step
                if (weights[i - 1] <= w) {
                    // Choose max of: including item i or not including it
                    dp[i][w] = Math.max(dp[i - 1][w],
                                        dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                } else {
                    // Cannot include item i
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Final answer is in dp[n][capacity]
        System.out.println("DP Max Value: " + dp[n][capacity]);
        System.out.println("DP Steps: " + steps);
    }
}



