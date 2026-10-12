class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int MOD = 1000000007;
        int[] bits = new int[31];

        for (int num : nums) {
            for (int i = 0; i < 31; i++) {
                if ((num & (1 << i)) != 0) {
                    bits[i]++;
                }
            }
        }

        long ans = 0;

        for (int i = 0; i < k; i++) {
            int num = 0;

            for (int j = 0; j < 31; j++) {
                if (bits[j] > 0) {
                    num |= (1 << j);
                    bits[j]--;
                }
            }

            ans = (ans + (long) num * num) % MOD;
        }

        return (int) ans;
    }
}