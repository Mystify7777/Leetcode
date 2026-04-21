// 1722. Minimize Hamming Distance After Swap Operations
// https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
class Solution {
    private int[] parent;

    private int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    private void unite(int a, int b) {
        parent[find(a)] = find(b);
    }

    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] swap : allowedSwaps) {
            unite(swap[0], swap[1]);
        }

        // Group source values by their component root
        Map<Integer, Map<Integer, Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(i);
            groups.computeIfAbsent(root, k -> new HashMap<>())
                  .merge(source[i], 1, Integer::sum);
        }

        int hammingDist = 0;
        for (int i = 0; i < n; i++) {
            int root = find(i);
            Map<Integer, Integer> freq = groups.get(root);
            if (freq.getOrDefault(target[i], 0) > 0) {
                freq.merge(target[i], -1, Integer::sum); // matched, consume this source value
            } else {
                hammingDist++;                           // no match found in this component
            }
        }

        return hammingDist;
    }
}

class Solution2 {
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int[] line = new int[source.length];
        Arrays.fill(line, 100001);
        for (int[] swap : allowedSwaps) {
            int root0 = getRoot(line, swap[0]);
            int root1 = getRoot(line, swap[1]);
            int min = Integer.min(root0, root1);
            line[root0] = min;
            line[root1] = min;
            // for speed up
            line[swap[0]] = min;
            line[swap[1]] = min;
        }
        HashMap<Integer, HashMap<Integer, Integer>> pool = new HashMap<>();
        int different = 0;
        int noSwap = 0;
        for (int i = 0; i < line.length; i++) {
            if (line[i] == 100001) {
                if (source[i] != target[i]) {
                    // not allow swap distance
                    noSwap++;
                }
                continue;
            }
            Integer root = getRoot(line, i);
            if (!pool.containsKey(root)) {
                pool.put(root, new HashMap<>());
            }
            HashMap<Integer, Integer> freq = pool.get(root);
            freq.put(source[i], freq.getOrDefault(source[i], 0)+1);
            freq.put(target[i], freq.getOrDefault(target[i], 0)-1);
        }
        for (HashMap<Integer, Integer> freq : pool.values()) {
            for (Integer value : freq.values()) {
                different += Math.abs(value);
            }
        }
        // two different number mean one hamming distance
        return different/2+noSwap;
    }
    
    private int getRoot(int[] line, int index) {
        if (line[index] == 100001) {
            line[index] = index;
            return index;
        }
        if (line[index] == index) {
            return index;
        }
        return getRoot(line, line[index]);
    }
}