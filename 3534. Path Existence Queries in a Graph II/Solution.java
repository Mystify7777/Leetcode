// 3534. Path Existence Queries in a Graph II
// https://leetcode.com/problems/path-existence-queries-in-a-graph-ii/
class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] newNums = new int[n][2];
        for (int i = 0; i < n; i++) newNums[i] = new int[]{nums[i], i};
        Arrays.sort(newNums, (a, b) -> a[0] - b[0]);

        int[] getI = new int[n];
        for (int i = 0; i < n; i++) getI[newNums[i][1]] = i;

        int[][] st = new int[n][18];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (r < i) r = i;
            while (r + 1 < n &&
                   newNums[r + 1][0] - newNums[r][0] <= maxDiff &&
                   newNums[r + 1][0] - newNums[i][0] <= maxDiff)
                r++;
            st[i][0] = r;
        }

        for (int j = 1; j < 18; j++)
            for (int i = 0; i < n; i++)
                st[i][j] = st[st[i][j - 1]][j - 1];

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int a = getI[queries[i][0]], b = getI[queries[i][1]];
            if (a > b) { int t = a; a = b; b = t; }
            if (a == b) { ans[i] = 0; continue; }

            int curr = a, steps = 0;
            for (int j = 17; j >= 0; j--)
                if (st[curr][j] < b) { curr = st[curr][j]; steps += (1 << j); }

            ans[i] = (st[curr][0] >= b) ? steps + 1 : -1;
        }
        return ans;
    }
}

class Solution2 {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int m = queries.length, l = 1;
        int[] ans = new int[m];
        long[] sorted = new long[n];
        for(int i = 0; i < n; i++) sorted[i] = (long)nums[i] << 32 | i;
        Arrays.sort(sorted);

        for(int i = 1; i < n; i <<= 1) l++;
        int[][] jumps = new int[l][n];

        int[] rank = new int[n];
        int right = 0;
        for(int i = 0; i < n; i++) {
            rank[(int)sorted[i]] = i;
            while(right < n && (sorted[right] >>> 32) <= (sorted[i] >>> 32) + maxDiff) right++;
            jumps[0][i] = right - 1;
        }
        for(int i = 1; i < l; i++) {
            for(int j = 0; j < n; j++) jumps[i][j] = jumps[i - 1][jumps[i - 1][j]];
        }
        for(int i = 0; i < m; i++) {
            int a = rank[queries[i][0]], b = rank[queries[i][1]];
            if(a > b) {
                int temp = a;
                a = b;
                b = temp;
            }
            ans[i] = jumps[l - 1][a] < b ? -1 : calcJumps(jumps, a, b, l);
        }
        return ans;
    }
    private int calcJumps(int[][] jumps, int a, int b, int right) {
        if(a == b) return 0;
        if(jumps[0][a] >= b) return 1;
        int left = 0;
        while(left < right) {
            int mid = left + right + 1 >>> 1;
            if(jumps[mid][a] < b) left = mid;
            else right = mid - 1;
        }
        return (1 << left) + calcJumps(jumps, jumps[left][a], b, left);
    }
}