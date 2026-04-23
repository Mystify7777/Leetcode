public // 2615. Sum of Distances
// https://leetcode.com/problems/sum-of-distances/
class Solution {
    public long[] distance(int[] nums) {
        int n = nums.length;
        long[] ans = new long[n];

        Map<Integer, List<Integer>> mp = new HashMap<>();

        for (int i = 0; i < n; i++) {
            mp.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        for (List<Integer> pos : mp.values()) {

            long sum = 0;
            for (int x : pos) sum += x;

            long leftSum = 0;
            int m = pos.size();

            for (int i = 0; i < m; i++) {
                long rightSum = sum - leftSum - pos.get(i);

                long left  = (long) pos.get(i) * i - leftSum;
                long right = rightSum - (long) pos.get(i) * (m - i - 1);

                ans[pos.get(i)] = left + right;

                leftSum += pos.get(i);
            }
        }

        return ans;
    }
}

class Solution2 {
    private class Data {
        long s1, s2;
        int c1, c2;
    }

    public long[] distance(int[] nums) {
        int n = nums.length;
        Map<Integer, Data> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Data d = m.get(nums[i]);
            if (d == null)
                m.put(nums[i], d = new Data());
            d.s2 += i;
            d.c2 += 1;
        }
        long[] r = new long[n];
        for (int i = 0; i < n; i++) {
            Data x = m.get(nums[i]);
            r[i] = x.s2 - x.s1 + 1L * x.c1 * i - 1L * i * x.c2;
            x.s1 += i;
            x.s2 -= i;
            x.c1 += 1;
            x.c2 -= 1;
        }
        return r;
    }
} Solution {
    
}
