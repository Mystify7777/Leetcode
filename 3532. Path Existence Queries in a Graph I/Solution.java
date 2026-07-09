// 3532. Path Existence Queries in a Graph I
// https://leetcode.com/problems/path-existence-queries-in-a-graph-i/
class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] comp = new int[n];
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] <= maxDiff) {
                comp[i] = comp[i - 1];
            } else {
                comp[i] = comp[i - 1] + 1;
            }
        }

        boolean[] ans = new boolean[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            ans[i] = (comp[u] == comp[v]);
        }

        return ans;
    }
}

class Solution2 {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        boolean[] res = new boolean[queries.length];

        int[] comp = new int[n];

        int com = 0;
        comp[0] = com;

        for (int i = 1; i < n; i++) {

            if (nums[i] - nums[i - 1] > maxDiff) {
                com++;
            }

            comp[i] = com;
        }


        for (int i = 0; i < queries.length; i++) {
            int source = queries[i][0];
            int dest = queries[i][1];

            if ((source == dest) || (comp[source] == comp[dest]))
                res[i] = true;
        }

        return res;
    }
}