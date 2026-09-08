class Solution {
    public boolean isScramble(String s1, String s2) {
        int n = s1.length();

        if (n != s2.length()) return false;
        if (s1.equals(s2)) return true;

        // dp[i][j][len]
        boolean[][][] dp = new boolean[n][n][n + 1];

        // Length 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }

        // Build answers for increasing lengths
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                for (int j = 0; j + len <= n; j++) {

                    for (int k = 1; k < len; k++) {

                        // No swap
                        if (dp[i][j][k] &&
                            dp[i + k][j + k][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }

                        // Swap
                        if (dp[i][j + len - k][k] &&
                            dp[i + k][j][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[0][0][n];
    }
}