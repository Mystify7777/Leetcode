//copypasted
//2147. Number of Ways to Divide a Long Corridor
//https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/
class Solution {
       public int numberOfWays(String s) {
        long res = 1, j = 0, k = 0, mod = (long)1e9 + 7;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == 'S') {
                if (++k > 2 && k % 2 == 1)
                    res = res * (i - j) % mod;
                j = i;
            }
        }
        return k % 2 == 0 && k > 0 ? (int)res : 0;
    }
}

/**
what is this implementation?

class Solution {
    public int numberOfWays(String corridor) {
        final int n = corridor.length();
        final byte[] corr = new byte[n];
        corridor.getBytes(0, n, corr, 0);
        
        long ways = 1;
        int seatCount = 0;
        int prevSeatIdx = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (corr[i] == 'P')  continue;
            if (seatCount != 0)
                prevSeatIdx = i;
            else if (prevSeatIdx > 0) 
                ways = (ways * (prevSeatIdx - i)) % 1_000_000_007L;
            seatCount ^= 1;
        }
        
        return (prevSeatIdx < 0 || seatCount != 0) ? 0 : (int)ways;
    }
}
 */