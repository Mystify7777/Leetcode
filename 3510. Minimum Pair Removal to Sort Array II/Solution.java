// 3510. Minimum Pair Removal to Sort Array II
//cp
//https://leetcode.com/problems/minimum-pair-removal-to-sort-array-ii/
class Solution {
    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;

        long[] arr = new long[n];
        for (int i = 0; i < n; ++i) arr[i] = nums[i];
        boolean[] removed = new boolean[n];

        PriorityQueue<P> pq = new PriorityQueue<>(new Comparator<P>() {
            public int compare(P a, P b) {
                if (a.sum < b.sum) return -1;
                if (a.sum > b.sum) return 1;
                return Integer.compare(a.idx, b.idx);
            }
        });

        int sorted = 0;
        for (int i = 1; i < n; ++i) {
            pq.add(new P(arr[i - 1] + arr[i], i - 1));
            if (arr[i] >= arr[i - 1]) sorted++;
        }
        if (sorted == n - 1) return 0;

        int rem = n;
        int[] prev = new int[n];
        int[] next = new int[n];
        for (int i = 0; i < n; ++i) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }

        while (rem > 1) {
            P top = pq.poll();
            if (top == null) break;
            long sum = top.sum;
            int left = top.idx;
            int right = next[left];
            if (right >= n || removed[left] || removed[right] || arr[left] + arr[right] != sum)
                continue;

            int pre = prev[left];
            int nxt = next[right];

            if (arr[left] <= arr[right]) sorted--;
            if (pre != -1 && arr[pre] <= arr[left]) sorted--;
            if (nxt != n && arr[right] <= arr[nxt]) sorted--;

            arr[left] += arr[right];
            removed[right] = true;
            rem--;

            if (pre != -1) {
                pq.add(new P(arr[pre] + arr[left], pre));
                if (arr[pre] <= arr[left]) sorted++;
            } else {
                prev[left] = -1;
            }

            if (nxt != n) {
                prev[nxt] = left;
                next[left] = nxt;
                pq.add(new P(arr[left] + arr[nxt], left));
                if (arr[left] <= arr[nxt]) sorted++;
            } else {
                next[left] = n;
            }

            if (sorted == rem - 1)
                return n - rem;
        }
        return n;
    }

        private static class P {
        long sum;
        int idx;
        P(long s, int i) {
            sum = s;
            idx = i;
        }
    }

}

class Solution2 {
    static class Node {
        long value;
        Node prev, next;
        boolean removed;
        int idx; // tie-breaker: leftmost representative

        Node(long value, int idx) {
            this.value = value;
            this.idx = idx;
        }
    }

    static class MinSumPair {
        long sum;
        Node left, right;
        int leftIdx;

        MinSumPair(Node left, Node right) {
            this.left = left;
            this.right = right;
            this.sum = left.value + right.value;
            this.leftIdx = left.idx;
        }
    }

    private static boolean isReal(Node x) {
        return x != null && x.idx != -1; // dummy has idx=-1
    }

    private static int invEdge(Node a, Node b) {
        if (!isReal(a) || b == null) {
            return 0;
        }
        return a.value > b.value ? 1 : 0;
    }

    public static int minimumPairRemoval(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        Node dummy = new Node(0, -1);
        Node cur = dummy;

        // Build DLL
        for (int i = 0; i < nums.length; i++) {
            Node node = new Node(nums[i], i);
            cur.next = node;
            node.prev = cur;
            cur = node;
        }

        PriorityQueue<MinSumPair> pq = new PriorityQueue<>(
                (a, b) -> {
                    int c = Long.compare(a.sum, b.sum);
                    if (c != 0) {
                        return c;
                    }
                    return Integer.compare(a.leftIdx, b.leftIdx);
                }
        );

        // Init inversions + heap
        int inv = 0;
        Node run = dummy.next;
        while (run != null && run.next != null) {
            inv += invEdge(run, run.next);
            pq.offer(new MinSumPair(run, run.next));
            run = run.next;
        }

        if (inv == 0) {
            return 0;
        }

        int ops = 0;

        while (inv > 0) {
            MinSumPair p = pq.poll();

            // Shouldn't happen if inv>0, but defensive
            if (p == null) {
                break;
            }

            Node left = p.left;
            Node right = p.right;

            // Lazy deletion / adjacency validation
            if (left.removed || right.removed) {
                continue;
            }
            if (left.next != right) {
                continue; // stale pair
            }

            // Commit merge
            ops++;

            Node prev = left.prev;     // could be dummy
            Node next = right.next;    // could be null

            // Remove inversions of disappearing edges
            inv -= invEdge(prev, left);
            inv -= invEdge(left, right);
            inv -= invEdge(right, next);

            // Create merged node (inherits leftmost idx)
            Node merged = new Node(left.value + right.value, left.idx);

            // Relink prev <-> merged
            prev.next = merged;
            merged.prev = prev;

            // Relink merged <-> next
            merged.next = next;
            if (next != null) {
                next.prev = merged;
            }

            // Mark old nodes removed
            left.removed = true;
            right.removed = true;

            // Add inversions of new edges
            inv += invEdge(prev, merged);
            inv += invEdge(merged, next);

            // Push new candidate pairs
            if (isReal(prev)) {
                pq.offer(new MinSumPair(prev, merged));
            }
            if (next != null) {
                pq.offer(new MinSumPair(merged, next));
            }
        }

        return ops;
    }
}