class Solution {
    public int minOperations(String s) {
        int n = s.length();
        char[] cs = s.toCharArray();

        boolean isSorted = true;
        char mn = cs[0];
        char mx = cs[0];

        for (int i = 0; i < n; i++) {
            if (i > 0 && cs[i] < cs[i - 1]) {
                isSorted = false;
            }
            if (cs[i] < mn) mn = cs[i];
            if (cs[i] > mx) mx = cs[i];
        }

        if (isSorted) {
            return 0;
        }
        if (n == 2) {
            return -1;
        }

        // Can sort in 1 operation if the global min is at index 0 or global max is at index n - 1
        if (cs[0] == mn || cs[n - 1] == mx) {
            return 1;
        }

        // Can sort in 2 operations if a global min or max lies in the interior
        for (int i = 1; i < n - 1; i++) {
            if (cs[i] == mn || cs[i] == mx) {
                return 2;
            }
        }

        // Otherwise (max is at index 0 and min is at index n - 1 exclusively)
        return 3;
    }
}