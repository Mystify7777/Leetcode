// 3161. Block Placement Queries
// https://leetcode.com/problems/block-placement-queries/
import java.util.*;

class Solution {

    private final int MAXX = 50000;
    private int[] seg;

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            seg[node] = val;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid)
            update(2 * node, l, mid, idx, val);
        else
            update(2 * node + 1, mid + 1, r, idx, val);

        seg[node] = Math.max(seg[2 * node], seg[2 * node + 1]);
    }

    private int query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l)
            return 0;

        if (ql <= l && r <= qr)
            return seg[node];

        int mid = (l + r) / 2;

        return Math.max(
            query(2 * node, l, mid, ql, qr),
            query(2 * node + 1, mid + 1, r, ql, qr)
        );
    }

    public List<Boolean> getResults(int[][] queries) {

        seg = new int[4 * (MAXX + 1)];

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);

        for (int[] q : queries) {
            if (q[0] == 1) obstacles.add(q[1]);
        }

        List<Integer> pos = new ArrayList<>(obstacles);

        for (int i = 1; i < pos.size(); i++) {
            update(1,0,MAXX,pos.get(i),pos.get(i) - pos.get(i - 1));
        }

        List<Boolean> ans = new ArrayList<>();

        for (int i = queries.length - 1; i >= 0; i--) {

            if (queries[i][0] == 2) {

                int x = queries[i][1];
                int sz = queries[i][2];

                int prevObstacle = obstacles.floor(x);

                int best = query(1, 0,MAXX,0,prevObstacle);
                best = Math.max(best, x - prevObstacle);

                ans.add(best >= sz);
            }
            else {

                int x = queries[i][1];

                Integer leftPos = obstacles.lower(x);

                update(1,0,MAXX,x,0);

                Integer rightPos = obstacles.higher(x);

                if (rightPos != null) {
                    update(1,0,MAXX,rightPos,rightPos - leftPos);
                }

                obstacles.remove(x);
            }
        }

        Collections.reverse(ans);
        return ans;
    }
}

class Solution2 {
    public List<Boolean> getResults(int[][] queries) {
        int max = 0;
        for(int[] q: queries)
            max = Math.max(max, q[1]);
        max += 2;
        boolean[] obs = new boolean[max+1];
        obs[0] = obs[max] = true;
        for(int[] q: queries)
            if(q[0] == 1)
                obs[q[1]] = true;
        int[] left = new int[max+1];
        int[] right = new int[max+1];
        for(int i = 0; i <= max; i++) 
            left[i] = obs[i] ? i : left[i-1];
        for(int i = max; i > -1; i--) 
            right[i] = obs[i] ? i : right[i+1];

        BIT bit = new BIT(max);
        int p = 0;
        for(int i = 1; i <= max; i++)
            if(obs[i]) {
                bit.update(i, i - p);
                p = i;
            }

        List<Boolean> res = new ArrayList<> ();
        for(int i = queries.length-1; i > -1; i--) {
            int[] q = queries[i];
            if(q[0] == 1) {
                int x = q[1];
                int prev = find(x-1, left);
                int nxt = find(x+1, right);
                bit.update(nxt, nxt - prev);

                obs[x] = false;
                left[x] = prev;
                right[x] = nxt;
            }
            else {
                int x = q[1], sz = q[2];
                int prev = find(x, left);
                int gap = x - prev;
                int maxGap = bit.query(prev);
                res.add(Math.max(maxGap, gap) >= sz);
            }
        }
        Collections.reverse(res);
        return res;
    }

    public int find(int x, int[] arr) {
        int root = x;
        while(arr[root] != root)
            root = arr[root];

        while(x != root) {
            int nxt = arr[x];
            arr[x] = root;
            x = nxt;
        }
        return root;
    }

    public class BIT {
        int[] arr;
        int sz;

        public BIT(int sz) {
            this.sz = sz + 1;
            arr = new int[this.sz];
        }

        public void update(int x, int v) {
            if(x <= 0)
                return;
            while(x < sz) {
                arr[x] = Math.max(arr[x], v);
                x += lowbit(x);
            }
        }

        public int query(int x) {
            int res = 0;
            while(x > 0) {
                res = Math.max(res, arr[x]);
                x -= lowbit(x);
            }
            return res;
        }

        public int lowbit(int x) {
            return x & -x;
        }
    }
}
