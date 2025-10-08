// 1488. Avoid Flood in The City
//https://leetcode.com/problems/avoid-flood-in-the-city
//copypasted
class Solution {
    public int[] avoidFlood(int[] rains) {
        // the previous appeared idx of rains[i]
        Map<Integer, Integer> map = new HashMap<>();
        TreeSet<Integer> zeros = new TreeSet<>();
        int[] res = new int[rains.length];
        for (int i = 0; i < rains.length; i++) {
            if (rains[i] == 0) {
                zeros.add(i);
            } else {
                if (map.containsKey(rains[i])) {
                    // find the location of zero that can be used to empty rains[i]
                    Integer next = zeros.ceiling(map.get(rains[i]));
                    if (next == null) return new int[0];
                    res[next] = rains[i];
                    zeros.remove(next);
                }
                res[i] = -1;
				map.put(rains[i], i);
            }
        }
        for (int i : zeros) res[i] = 1;
        return res;
    }
}
/**

class Solution {
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        int[] fa = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            fa[i] = i;
        }

        int[] ans = new int[n];
        Map<Integer, Integer> fullDay = new HashMap<>(); // lake -> 装满日
        for (int i = 0; i < n; i++) {
            int lake = rains[i];
            if (lake == 0) {
                ans[i] = 1; // 先随便选一个湖抽干
                continue;
            }
            Integer j = fullDay.get(lake);
            if (j != null) {
                // 必须在 j 之后，i 之前把 lake 抽干
                // 选一个最早的未被使用的抽水日，如果选晚的，可能会导致其他湖没有可用的抽水日
                int dryDay = find(j + 1, fa);
                if (dryDay >= i) {
                    return new int[]{}; // 无法阻止洪水
                }
                ans[dryDay] = lake;
                fa[dryDay] = find(dryDay + 1, fa); // 删除 dryDay
            }
            ans[i] = -1;
            fa[i] = i + 1; // 删除 i
            fullDay.put(lake, i); // 插入或更新装满日
        }
        return ans;
    }

    private int find(int x, int[] fa) {
        if (fa[x] != x) {
            fa[x] = find(fa[x], fa);
        }
        return fa[x];
    }
} */