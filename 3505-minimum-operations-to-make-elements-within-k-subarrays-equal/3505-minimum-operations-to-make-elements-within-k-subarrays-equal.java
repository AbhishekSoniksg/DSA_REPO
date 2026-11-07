
import java.util.*;

class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    int leftSize = 0, rightSize = 0;
    long leftSum = 0, rightSum = 0;

    void add(int num) {
        if (left.isEmpty() || num <= left.lastKey()) {
            left.merge(num, 1, Integer::sum);
            leftSize++;
            leftSum += num;
        } else {
            right.merge(num, 1, Integer::sum);
            rightSize++;
            rightSum += num;
        }
        balance();
    }

    void remove(int num) {
        if (left.containsKey(num)) {
            removeFrom(left, num);
            leftSize--;
            leftSum -= num;
        } else {
            removeFrom(right, num);
            rightSize--;
            rightSum -= num;
        }
        balance();
    }

    void removeFrom(TreeMap<Integer, Integer> map, int num) {
        int count = map.get(num);
        if (count == 1) {
            map.remove(num);
        } else {
            map.put(num, count - 1);
        }
    }

    void balance() {
        while (leftSize > rightSize + 1) {
            int num = left.lastKey();
            removeFrom(left, num);
            leftSize--;
            leftSum -= num;

            right.merge(num, 1, Integer::sum);
            rightSize++;
            rightSum += num;
        }

        while (leftSize < rightSize) {
            int num = right.firstKey();
            removeFrom(right, num);
            rightSize--;
            rightSum -= num;

            left.merge(num, 1, Integer::sum);
            leftSize++;
            leftSum += num;
        }
    }

    long getCost() {
        long median = left.lastKey();

        return median * leftSize - leftSum
             + rightSum - median * rightSize;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        for (int i = 0; i < x; i++) {
            add(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            remove(nums[i - x]);
            add(nums[i]);
            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[n + 1][k + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {

                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);

                if (j > 0 && i >= x) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
