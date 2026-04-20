// 2078. Two Furthest Houses With Different Colors
// https://leetcode.com/problems/two-furthest-houses-with-different-colors/
class Solution {
    public int maxDistance(int[] A) {
        int n = A.length;
        int left = 0, right = 0;

        for (int i = 0; i < n; i++)
            if (A[i] != A[n - 1]) {
                left = i;
                break;
            }

        for (int i = n - 1; i >= 0; i--)
            if (A[i] != A[0]) {
                right = i;
                break;
            }

        return Math.max(n - 1 - left, right);
    }
}
// Alternate
class Solution2 {
    public int maxDistance(int[] A) {
        int j = A.length;

        for (int i = 0; i < j; i++)
            if (A[i] != A[j - 1] || A[j - 1 - i] != A[0])
                return j - 1 - i;

        return 0;
    }
}