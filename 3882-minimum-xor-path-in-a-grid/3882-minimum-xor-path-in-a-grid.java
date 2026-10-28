class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int maxVal = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxVal = Math.max(maxVal, grid[i][j]);
            }
        }

        int maxPossible = 1;
        while (maxPossible <= maxVal || maxPossible < 1024) {
            maxPossible <<= 1;
        }

        boolean[][][] reachable = new boolean[m][n][maxPossible];
        reachable[0][0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int x = 0; x < maxPossible; x++) {
                    if (!reachable[i][j][x]) {
                        continue;
                    }

                    if (i + 1 < m) {
                        reachable[i + 1][j][x ^ grid[i + 1][j]] = true;
                    }

                    if (j + 1 < n) {
                        reachable[i][j + 1][x ^ grid[i][j + 1]] = true;
                    }
                }
            }
        }

        for (int x = 0; x < maxPossible; x++) {
            if (reachable[m - 1][n - 1][x]) {
                return x;
            }
        }

        return 0;
    }
}