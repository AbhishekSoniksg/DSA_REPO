class Solution {
    public int numOfSubarrays(int[] arr) {
        int oddCount = 0, evenCount = 1;
        int prefixSum = 0, result = 0;
        int mod = 1000000007;
        
        for (int num : arr) {
            prefixSum += num;
            if (prefixSum % 2 == 1) {
                result = (result + evenCount) % mod;
                oddCount++;
            } else {
                result = (result + oddCount) % mod;
                evenCount++;
            }
        }
        
        return result;
    }
}