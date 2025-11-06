// 3607. Power Grid Maintenance
// https://leetcode.com/problems/power-grid-maintenance/
class DSU {
    int[] parent;

    public DSU(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++)
            parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    public boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py)
            return false;
        parent[py] = px;
        return true;
    }
}

class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        DSU dsu = new DSU(c);
        boolean[] online = new boolean[c + 1];
        Arrays.fill(online, true);

        for (int[] conn : connections)
            dsu.union(conn[0], conn[1]);

        Map<Integer, PriorityQueue<Integer>> componentHeap = new HashMap<>();
        for (int station = 1; station <= c; station++) {
            int root = dsu.find(station);
            componentHeap.putIfAbsent(root, new PriorityQueue<>());
            componentHeap.get(root).offer(station);
        }

        List<Integer> result = new ArrayList<>();

        for (int[] query : queries) {
            int type = query[0], x = query[1];

            if (type == 2) {
                online[x] = false;
            } else {
                if (online[x]) {
                    result.add(x);
                } else {
                    int root = dsu.find(x);
                    PriorityQueue<Integer> heap = componentHeap.get(root);

                    while (heap != null && !heap.isEmpty() && !online[heap.peek()]) {
                        heap.poll();
                    }

                    result.add((heap == null || heap.isEmpty()) ? -1 : heap.peek());
                }
            }
        }

        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }
}
//alternate Approach
/**
class Solution {
    public int[] processQueries(int n, int[][] connections, int[][] queries) {
        n++;
        final int[] l = new int[n];
        for (int i = 1; i < n; i++) {
            l[i] = i;
        }
        for (int[] as : connections) {
            l[getLabel(l, as[0])] = l[getLabel(l, as[1])];
        }
        final int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            counts[getLabel(l, i)]++;
        }
        updateCounts(counts);
        final int[] starts = counts.clone();
        final int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[counts[l[i]]++] = i;
        }
        final int[] r = new int[queries.length];
        int len = 0;
        final boolean[] offline = new boolean[n];
        for (var q: queries) {
            final int x = q[1];
            if (q[0] == 1) {
                if (offline[x]) {
                    final int label = l[x];
                    final int end = counts[label];
                    int start = starts[label];
                    while (start < end && offline[sorted[start]]) {
                        start++;
                    }
                    starts[label] = start;
                    r[len++] = start == end ? -1 : sorted[start];
                } else {
                    r[len++] = x;
                }
            } else {
                offline[x] = true;
            }
        }
        return Arrays.copyOf(r, len);
    }


    static final int[] arr = new int[100_001];

    private static int check(int[] sorted, int[] source, int[] target, int start, int end) {
        int r = 0;
        for (int i = start; i < end; i++) {
            final int idx = sorted[i];
            if (++arr[source[idx]] > 0) r++;
            if (arr[target[idx]]-- > 0) r--;
        }
        for (int i = start; i < end; i++) {
            final int idx = sorted[i];
            arr[source[idx]] = 0;
            arr[target[idx]] = 0;
        }
        return r;
    }

    private static void updateCounts(int[] count) {
        int sum = 0;
        for (int r = 0; r < count.length; r++) {
            final int newSum = sum + count[r];
            count[r] = sum;
            sum = newSum;
        }
    }

    static int getLabel(final int[] labels, int idx) {
        final int current = labels[idx];
        return (current == idx || current < 0)? idx : (labels[idx] = getLabel(labels, current));
    }
}
 */