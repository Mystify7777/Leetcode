// 3653. XOR After Range Multiplication Queries I
// https://leetcode.com/problems/xor-after-range-multiplication-queries-i/
class Solution {
    final int mod = 1000000007;

    public int xorAfterQueries(int[] nums, int[][] queries) {

        // Process each query
        for (int[] t : queries) {
            int l = t[0];
            int r = t[1];
            int k = t[2];
            int v = t[3];

            int idx = l;

            // Apply operation at step k
            while (idx <= r) {
                long temp = nums[idx];
                nums[idx] = (int)((temp * v) % mod);
                idx += k;
            }
        }

        // Compute XOR of final array
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }

        return ans;
    }
}

class Solution2 {
    long MOD =(long)10e8+7;
    public int xorAfterQueries(int[] nums, int[][] q) {
        int n= nums.length;
        long arr[] =new long[n];
        for(int i=0;i<n;i++){
            arr[i] = (long)nums[i];
        }
        for(int i=0;i<q.length;i++){
            for(int j=q[i][0];j<=q[i][1];j+=q[i][2]){
                arr[j]=(arr[j]*(long)q[i][3])%MOD;
            }
        }
        long ans =0;
        for(int i=0;i<n;i++){
            ans = ans^arr[i];
        }
        return (int)ans;
    }
}