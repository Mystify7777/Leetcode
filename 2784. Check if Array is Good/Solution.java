// 2784. Check if Array is Good
// https://leetcode.com/problems/check-if-array-is-good/
import java.util.*;

class Solution {
    public boolean isGood(int[] nums) {
        int n = nums.length - 1;
        Set<Integer> seen = new HashSet<>();
        boolean dup = false;

        for (int num : nums) {
            if (num > n) return false;

            if (seen.contains(num)) {
                if (num < n || dup) return false;
                dup = true;
                continue;
            }

            seen.add(num);
        }

        return true;
    }
}

// playing with the constraints
class Solution2 {
    public boolean isGood(int[] nums) {

        int n = 0;

        // find max element
        for (int num : nums) {
            n = Math.max(n, num);
        }

        // size check
        if (nums.length != n + 1) return false;

        int[] freq = new int[201];

        for (int num : nums) {
            freq[num]++;
        }

        for (int i = 1; i < n; i++) {
            if (freq[i] != 1) return false;
        }

        return freq[n] == 2;
    }
}
