class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        boolean isInc = true;
        boolean isDec = true;

        for (int i = 0; i < n; i++) {
            if ((nums[i] + 1) % n != nums[(i + 1) % n]) {
                isInc = false;
            }
            if ((nums[i] - 1 + n) % n != nums[(i + 1) % n]) {
                isDec = false;
            }
        }

        if (!isInc && !isDec) {
            return -1;
        }

        int idx0 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                idx0 = i;
                break;
            }
        }

        if (isInc) {
            int op1 = idx0;
            int op2 = (n - idx0) % n + 2;
            return Math.min(op1, op2);
        } else {
            int op1 = (n - 1 - idx0) % n + 1;
            int op2 = (idx0 + 1) % n + 1;
            return Math.min(op1, op2);
        }
    }
}