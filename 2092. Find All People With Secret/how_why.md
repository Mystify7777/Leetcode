# 2092. Find All People With Secret — how/why

## Recap

At time 0, person 0 and person `firstPerson` know a secret. Given a list of meetings `[person_a, person_b, time]`, where two people meet at a specific time and exchange information they know, find all people who know the secret.

A person learns the secret if:

1. They meet someone who already knows the secret.
2. The meeting happens at any time (there is no order constraint; any two people who meet will exchange all known information).

## Intuition

This is a graph connectivity problem where meetings establish relationships. However, the key insight is **temporal constraint**: a meeting at time `t` only allows secret propagation if at least one participant already knew the secret *before or at time `t`*.

**Naive approach**: Simulate meetings in chronological order, mark people as knowing the secret. However, a single pass isn't enough because meetings at the same time can create transitive connections (A meets B who meets C at time t; even if only A knows the secret, both B and C should learn it through the chain).

**Better approach**: Process meetings grouped by time:

1. For each distinct time `t`, connect all people meeting at time `t` using Union-Find.
2. After connecting, check which components include person 0 (who knows the secret).
3. If a person was connected at time `t` but is **not** in the same component as person 0, they didn't know the secret before this meeting, so reset their union-find (disconnect them).
4. This ensures the secret only propagates if someone in the meeting group already knew it.

## Approach

**Time-Grouped Union-Find with Reset**:

1. Sort meetings by time.
2. Initialize Union-Find with each person as their own component; union person 0 with `firstPerson`.
3. Iterate through meetings grouped by time:
   - For each group at time `t`:
     - Union all pairs in the meeting group (connecting people who meet).
     - Check each person in the group: if they're not connected to person 0, reset them (disconnect from their component).
   - This "reset" ensures meetings only propagate the secret if someone in the group already knew it.
4. After processing all meetings, collect all people connected to person 0.

**Why reset works**: If a person wasn't connected to person 0 before time `t`, they couldn't have learned the secret. Even though they got connected during time `t` meetings, if those connections don't include person 0's component, the secret wasn't present in the meeting. Resetting them prevents invalid propagation.

## Code (Java)

```java
class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);

        UF uf = new UF(n);
        uf.union(0, firstPerson);

        int i = 0;
        while (i < meetings.length) {
            int curTime = meetings[i][2];
            Set<Integer> pool = new HashSet<>();

            while (i < meetings.length && curTime == meetings[i][2]) {
                int[] currentMeeting = meetings[i];
                uf.union(currentMeeting[0], currentMeeting[1]);
                pool.add(currentMeeting[0]);
                pool.add(currentMeeting[1]);
                i++;
            }

            for (int j : pool) {
                if (!uf.connected(0, j)) uf.reset(j);
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (uf.connected(j, 0)) ans.add(j);
        }
        return ans;
    }

    private static class UF {
        int[] parent, rank;

        public UF(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);

            if (rootP == rootQ) return;

            if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
        }

        public int find(int p) {
            while (parent[p] != p) {
                p = parent[parent[p]];
            }
            return p;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void reset(int p) {
            parent[p] = p;
            rank[p] = 0;
        }
    }
}
```

## Correctness

- **Sorting by time**: Ensures meetings are processed in chronological order.

- **Union at same time**: Connecting all people at time `t` captures transitive meetings (A meets B, B meets C at the same time; all three should be connected).

- **Reset after connectivity check**: If a person in the pool is not connected to person 0 after unions, they didn't know the secret before time `t`, so resetting prevents false propagation.

- **Final connectivity check**: A person knows the secret if and only if they're in the same component as person 0 (who always knows it).

- **Rank-based union**: Ensures near-optimal union-find performance with path compression.

## Complexity

- **Time**: O(m log m + (m + n) α(n)) where m = number of meetings, α is inverse Ackermann (nearly constant). Sorting dominates; union-find operations are nearly O(1) per operation.
- **Space**: O(n) for union-find structure + O(m) for temporary pool set.

## Edge Cases

- No meetings: Only persons 0 and `firstPerson` know the secret → return `[0, firstPerson]`.
- All meetings involve person 0: All met people learn the secret → return all attendees plus 0.
- Disjoint meetings: If meetings never connect to person 0, only return `[0, firstPerson]`.
- Multiple meetings at same time: All connected through transitive unions; reset ensures correct secret propagation.
- `firstPerson == 0`: Redundant union; doesn't affect correctness.

## Takeaways

- **Time-grouped processing**: Grouping events by timestamp and processing together captures transitive connections.

- **Union-Find with reset**: A creative use of union-find where we selectively disconnect components that don't contain the "source" (person 0).

- **Temporal semantics**: The key challenge is ensuring meetings only propagate secrets if someone in the meeting group already knew. Reset elegantly handles this.

- **Component-based reasoning**: Once the union-find is correctly set up, the answer is simply all people in person 0's component.

## Alternative (Array-Based Time Buckets, O(m + n))

```java
class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        parent[firstPerson] = 0;

        int maxTime = 0;
        for (int[] meet : meetings) {
            maxTime = Math.max(maxTime, meet[2]);
        }

        List<int[]>[] timeArray = new List[maxTime + 1];
        for (int[] meet : meetings) {
            if (timeArray[meet[2]] == null) {
                timeArray[meet[2]] = new ArrayList<>();
            }
            timeArray[meet[2]].add(new int[]{meet[0], meet[1]});
        }

        for (int i = 1; i < timeArray.length; i++) {
            if (timeArray[i] != null) {
                for (int[] meeting : timeArray[i]) {
                    union(meeting[0], meeting[1], parent);
                }
                for (int[] meeting : timeArray[i]) {
                    int u = meeting[0];
                    int v = meeting[1];
                    if (find(u, parent) != 0) {
                        parent[u] = u;
                    }
                    if (find(v, parent) != 0) {
                        parent[v] = v;
                    }
                }
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == 0) {
                results.add(i);
            }
        }

        return results;
    }

    private void union(int u, int v, int[] parent) {
        int uRoot = find(u, parent);
        int vRoot = find(v, parent);
        if (uRoot < vRoot) {
            parent[vRoot] = uRoot;
        } else {
            parent[uRoot] = vRoot;
        }
    }

    private int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }
}
```

**Trade-off**: This alternative uses an array-based bucket `timeArray[time]` to group meetings instead of sorting. It's O(m + n) for indexing if maxTime is small, but requires O(maxTime) space. When maxTime is very large, sorting becomes preferable. Use sorting for robustness across all input sizes; use bucketing when maxTime is guaranteed small.
