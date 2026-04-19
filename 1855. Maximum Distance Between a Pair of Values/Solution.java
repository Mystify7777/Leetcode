// 1855. Maximum Distance Between a Pair of Values
// https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
class Solution {
    public int maxDistance(int[] A, int[] B) {
        int i, j = 1;

        for (i = 0; i < A.length && j < B.length; j++)
            if (A[i] > B[j])
                i++;

        return j - i - 1;
    }
}