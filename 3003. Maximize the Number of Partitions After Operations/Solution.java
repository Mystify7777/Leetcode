//copypasted
//3003. Maximize the Number of Partitions After Operations
// https://leetcode.com/problems/maximize-the-number-of-partitions-after-operations
class Solution {
    private HashMap<Long, Integer> cache;
    private String s;
    private int k;

    public int maxPartitionsAfterOperations(String s, int k) {
        this.cache = new HashMap<>();
        this.s = s;
        this.k = k;
        return dp(0, 0, true) + 1;
    }

    private int dp(int index, int currentSet, boolean canChange) {
        long key = ((long) index << 27)
                | ((long) currentSet << 1)
                | (canChange ? 1 : 0);

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (index == s.length()) {
            return 0;
        }

        int characterIndex = s.charAt(index) - 'a';
        int currentSetUpdated = currentSet | (1 << characterIndex);
        int distinctCount = Integer.bitCount(currentSetUpdated);

        int res;
        if (distinctCount > k) {
            res = 1 + dp(index + 1, 1 << characterIndex, canChange);
        } else {
            res = dp(index + 1, currentSetUpdated, canChange);
        }

        if (canChange) {
            for (int newCharIndex = 0; newCharIndex < 26; newCharIndex++) {
                int newSet = currentSet | (1 << newCharIndex);
                int newDistinctCount = Integer.bitCount(newSet);

                if (newDistinctCount > k) {
                    res = Math.max(res, 1 + dp(index + 1, 1 << newCharIndex, false));
                } else {
                    res = Math.max(res, dp(index + 1, newSet, false));
                }
            }
        }

        cache.put(key, res);
        return res;
    }
}
/**
class Solution {
    public int maxPartitionsAfterOperations(String S, int k) {
        if(k == 26) return 1;
        char[] s = S.toCharArray();
        int n = s.length;
        int[] left1 = new int[n], left2 = new int[n];
        int[] partitions = new int[n];
        int mask1 = 0, mask2 = 0, count = 1;
        for(int i = 0; i < n; i++) {
            int filter = 1 << (s[i] - 'a');
            mask2 |= mask1 & filter;
            mask1 |= filter;
            if(Integer.bitCount(mask1) > k) {
                mask1 = filter;
                mask2 = 0;
                count++;
            }
            left1[i] = mask1;
            left2[i] = mask2;
            partitions[i] = count;
        }
        int ans = count;
        mask1 = mask2 = count = 0;
        for(int i = n - 1; i >= 0; i--) {
            int filter = 1 << (s[i] - 'a');
            mask2 |= mask1 & filter;
            mask1 |= filter;
            if(Integer.bitCount(mask1) > k) {
                mask1 = filter;
                mask2 = 0;
                count++;
            }
            if(Integer.bitCount(mask1) == k) {
                if((filter & mask2) != 0 && (filter & left2[i]) != 0 && Integer.bitCount(left1[i]) == k && (left1[i] | mask1) != (1 << 26) - 1) ans = Math.max(ans, count + partitions[i] + 2);
                else if(mask2 != 0) ans = Math.max(ans, count + partitions[i] + 1);
            }
        }
        return ans;
    }
}
 */