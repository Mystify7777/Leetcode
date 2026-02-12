// 3721. Longest Balanced Subarray II
// https://leetcode.com/problems/longest-balanced-subarray-ii/
// semicolons : 1
class Solutioncreepy {

    //what is this?

    public int longestBalanced(int[] nums) {
        return CompletableFuture.supplyAsync(() -> Optional.of(new Object[]{new int[3][4 * nums.length], new HashMap<Integer, Integer>(), null}) .map(s -> Optional.of((BiFunction<Object, int[], Integer>) (f, a) -> a[0] == 0 ? (((int[][])s[0])[2][a[1]] == 0 ? 0 : (((int[][])s[0])[0][a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (((int[][])s[0])[1][a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (a[2] == a[3] ? 0 : (((int[][])s[0])[2][2 * a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (((int[][])s[0])[2][2 * a[1] + 1] += ((int[][])s[0])[2][a[1]]) * 0) + (((int[][])s[0])[2][a[1]] = 0) * 0) : a[0] == 1 ? ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) * 0 + (a[2] > a[5] || a[3] < a[4] ? 0 : a[4] <= a[2] && a[3] <= a[5] ? (((int[][])s[0])[2][a[1]] += a[6]) * 0 + ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{1, 2 * a[1], a[2], (a[2] + a[3]) / 2, a[4], a[5], a[6]}) * 0 + ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{1, 2 * a[1] + 1, (a[2] + a[3]) / 2 + 1, a[3], a[4], a[5], a[6]}) * 0 + (((int[][])s[0])[0][a[1]] = Math.min(((int[][])s[0])[0][2 * a[1]], ((int[][])s[0])[0][2 * a[1] + 1])) * 0 + (((int[][])s[0])[1][a[1]] = Math.max(((int[][])s[0])[1][2 * a[1]], ((int[][])s[0])[1][2 * a[1] + 1])) * 0) : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) * 0 + (((int[][])s[0])[0][a[1]] > 0 || ((int[][])s[0])[1][a[1]] < 0 ? -1 : a[2] == a[3] ? (((int[][])s[0])[0][a[1]] == 0 ? a[2] : -1) : Optional.of(((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{2, 2 * a[1], a[2], (a[2] + a[3]) / 2})).map(r -> r != -1 ? r : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{2, 2 * a[1] + 1, (a[2] + a[3]) / 2 + 1, a[3]})).get())) .map(func -> ((s[2] = func) == null ? s : s)) .map(state -> IntStream.range(0, nums.length).boxed().reduce(0, (max, r) -> Optional.of(nums[r] % 2 == 0 ? 1 : -1).map(val -> (state[1] != null && ((Map)state[1]).containsKey(nums[r]) ? ((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{1, 1, 0, nums.length - 1, 0, (int)((Map)state[1]).get(nums[r]), -val}) : 0) * 0 + ((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{1, 1, 0, nums.length - 1, 0, r, val}) * 0 + (((Map)state[1]).put(nums[r], r) == null ? 0 : 0) * 0 + Optional.of(((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{2, 1, 0, nums.length - 1})).filter(l -> l != -1 && l <= r).map(l -> Math.max(max, r - l + 1)).orElse(max)).get(), (a, b) -> b)) .get()).orElse(0)).join();
    }
}

class SegmentTree {
    /* Segment Tree over array of size n */

    public int n;
    public int size;
    public int[] sum;
    public int[] mn;
    public int[] mx;

    SegmentTree(int n_) {
        n = n_;
        size = 4 * n_;
        sum = new int[size];
        mn  = new int[size];
        mx  = new int[size];
    }

    void _pull(int node) {
        /* Helper to recompute information of node by it's children */

        int l = node * 2, r = node * 2 + 1;

        sum[node] = sum[l] + sum[r];
        mn[node] = Math.min(mn[l], sum[l] + mn[r]);
        mx[node] = Math.max(mx[l], sum[l] + mx[r]);
    }

    void update(int idx, int val) {
        /* Update value by index idx in original array */

        int node = 1, l = 0, r = n - 1;
        int[] path = new int[32]; // enough for n up to 2^31
        int ps = 0;

        while (l != r) {
            path[ps++] = node;
            int m = l + (r - l) / 2;
            if (idx <= m) {
                node = node * 2;
                r = m;
            } else {
                node = node * 2 + 1;
                l = m + 1;
            }
        }

        sum[node] = val;
        mn[node] = val;
        mx[node] = val;

        while (ps > 0) {
            _pull(path[--ps]);
        }
    }

    int find_rightmost_prefix(int target) {
        /* Find rightmost index r with prefixsum(r) = target */

        int node = 1, l = 0, r = n - 1, sum_before = 0;

        if (!(mn[node] <= target - sum_before && target - sum_before <= mx[node]))
            return -1;

        while (l != r) {
            int m = l + (r - l) / 2;
            int lchild = node * 2, rchild = node * 2 + 1;

            // Check right half first
            int sum_before_right = sum[lchild] + sum_before;
            int need_right = target - sum_before_right;

            if (mn[rchild] <= need_right && need_right <= mx[rchild]) {
                node = rchild;
                l = m + 1;
                sum_before = sum_before_right;
            } else {
                node = lchild;
                r = m;
            }
        }

        return l;
    }
}

class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;

        SegmentTree stree = new SegmentTree(n);  // SegmentTree over balance array for current l
        java.util.HashMap<Integer, Integer> first = new java.util.HashMap<>();  // val -> first occurence idx for current l

        int result = 0;
        for (int l = n - 1; l >= 0; --l) {
            int num = nums[l];

            // If x already had a first occurrence to the right, remove that old marker.
            Integer old = first.get(num);
            if (old != null)
                stree.update(old, 0);

            // Now x becomes first occurrence at l.
            first.put(num, l);
            stree.update(l, (num % 2 == 0) ? 1 : -1);

            // Find rightmost r >= l such that sum(w[l..r]) == 0
            int r = stree.find_rightmost_prefix(0);
            if (r >= l)
                result = Math.max(result, r - l + 1);
        }

        return result;
    }
}
