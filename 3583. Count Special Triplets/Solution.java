// 3583. Count Special Triplets
// https://leetcode.com/problems/count-special-triplets/
class Solution {
    final static int mod=(int)1e9+7, M=(int)1e5+1;
    public int specialTriplets(int[] nums) {
        final int  n=nums.length;
        int [] freq=new int[M];
        int [] prev=new int[M];
        for(int x: nums) freq[x]++;
        long cnt=0;
        prev[nums[0]]++;
        for(int i=1; i<n-1; i++){
            final int x=nums[i], x2=x<<1;
            if (x2<M)
                cnt+=(long)prev[x2]*(freq[x2]-prev[x2]-(x==0?1:0));
            prev[x]++;
        }
        return (int)(cnt%mod);
    }
}
/**
class Solution {
    static int[] f = new int[100001];
    static int[] r = new int[100001];
    final int MOD = 1_000_000_007;
    public int specialTriplets(int[] nums) {
        for(int n: nums) {
            r[n]++;
        }
        int count = 0, t;
        for(int n: nums) {
            r[n]--;
            t = n << 1;
            if(t < f.length) {
            count = (count + (int)((1L * f[t] * r[t]) % MOD)) % MOD;
            }
            f[n]++;
        }
        for(int n: nums) {
            f[n] = 0;
        }
        return count;
    }
}
 */