package com.github.creme332.utils;

public class FuzzySearching {

    private FuzzySearching() {

    }

    /**
     * Calculates the Levenshtein distance between two strings.
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return int The Levenshtein distance between the two strings.
     */
    public static int levenshteinDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        // Initialize a 2D array to store the distances
        int[][] dp = new int[m + 1][n + 1];

        // Fill the first row and column of the array
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Calculate distances using dynamic programming
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost));
            }
        }

        // Return the final result, which is the distance between the two strings
        return dp[m][n];
    }

    /**
     * Checks if the Levenshtein distance between two strings is 3 or less.
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return boolean True if the Levenshtein distance is 3 or less, otherwise
     *         false.
     */
    public static boolean isSimilar(String str1, String str2) {
        return levenshteinDistance(str1, str2) <= 4;
    }
}
