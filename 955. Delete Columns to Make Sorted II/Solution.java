// 955. Delete Columns to Make Sorted II
//https://leetcode.com/problems/delete-columns-to-make-sorted-ii/
class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        // Convert strings to char arrays once
        // This avoids repeated charAt() calls inside nested loops
        char[][] a = new char[n][];
        for (int i = 0; i < n; i++) {
            a[i] = strs[i].toCharArray();
        }

        // resolved[i] = true means:
        // strs[i] is already strictly smaller than strs[i+1]
        // considering previously kept columns
        boolean[] resolved = new boolean[n - 1];

        // Number of adjacent row pairs whose order is still undecided
        int unresolved = n - 1;

        int deletions = 0;

        // Process columns left to right
        for (int col = 0; col < m && unresolved > 0; col++) {
            boolean needDelete = false;

            // Check if keeping this column breaks lexicographical order
            for (int row = 0; row < n - 1; row++) {
                // Only compare rows whose order is not yet fixed
                if (!resolved[row] && a[row][col] > a[row + 1][col]) {
                    needDelete = true;
                    break;
                }
            }

            // If this column violates order, delete it
            if (needDelete) {
                deletions++;
                continue;
            }

            // Otherwise, update which row pairs become strictly ordered
            for (int row = 0; row < n - 1; row++) {
                if (!resolved[row] && a[row][col] < a[row + 1][col]) {
                    resolved[row] = true;
                    unresolved--;
                }
            }
        }

        return deletions;
    }
}
/**
class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        boolean[] sorted = new boolean[n - 1];
        int deletions = 0;

        for (int col = 0; col < m; col++) {
            boolean needDelete = false;

            // Check if this column breaks order
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row]) {
                    if (strs[row].charAt(col) > strs[row + 1].charAt(col)) {
                        needDelete = true;
                        break;
                    }
                }
            }

            // If invalid, delete this column
            if (needDelete) {
                deletions++;
                continue;
            }

            // Update sorted status
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row] &&
                    strs[row].charAt(col) < strs[row + 1].charAt(col)) {
                    sorted[row] = true;
                }
            }
        }

        return deletions;
    }
}
 */