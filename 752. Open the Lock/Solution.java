// 752. Open the Lock
//explain it to me in simpler terms and with an example walkthrough
//copypasted
import java.util.*;

class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadendSet = new HashSet<>(Arrays.asList(deadends));
        if (deadendSet.contains("0000")) {
            return -1;
        }
        
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>("0000", 0));
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        
        while (!queue.isEmpty()) {
            Pair<String, Integer> current = queue.poll();
            String currentCombination = current.getKey();
            int moves = current.getValue();
            
            if (currentCombination.equals(target)) {
                return moves;
            }
            
            for (int i = 0; i < 4; i++) {
                for (int delta : new int[]{-1, 1}) {
                    int newDigit = (currentCombination.charAt(i) - '0' + delta + 10) % 10;
                    String newCombination = currentCombination.substring(0, i) +
                                             newDigit +
                                             currentCombination.substring(i + 1);
                    
                    if (!visited.contains(newCombination) && !deadendSet.contains(newCombination)) {
                        visited.add(newCombination);
                        queue.offer(new Pair<>(newCombination, moves + 1));
                    }
                }
            }
        }
        
        return -1; // Target is not reachable
    }
}

//read and explain this please 
/**
import java.util.*;

class Solution {
    private static final int[] P10 = {1, 10, 100, 1000};

    public int openLock(String[] deadends, String target) {
        boolean[] dead = new boolean[10000];
        for (String s : deadends) dead[toInt(s)] = true;

        int tgt = toInt(target);
        if (dead[0] || dead[tgt]) return -1;
        if (tgt == 0) return 0;

        // två fronter (köer) + seen för respektive sida
        int[] fq = new int[10000], bq = new int[10000];
        int fh = 0, ft = 0, bh = 0, bt = 0;
        boolean[] fSeen = new boolean[10000], bSeen = new boolean[10000];

        fq[ft++] = 0;      fSeen[0] = true;
        bq[bt++] = tgt;    bSeen[tgt] = true;

        int steps = 0;
        while (fh < ft && bh < bt) {
            // expandera den mindre fronten
            if (ft - fh > bt - bh) {
                int[] tq = fq; fq = bq; bq = tq;
                int th = fh; fh = bh; bh = th;
                int tt = ft; ft = bt; bt = tt;
                boolean[] ts = fSeen; fSeen = bSeen; bSeen = ts;
            }

            int size = ft - fh;
            for (int s = 0; s < size; s++) {
                int cur = fq[fh++];
                if (bSeen[cur]) return steps; // möttes
                for (int i = 0; i < 4; i++) {
                    int d = (cur / P10[i]) % 10;

                    // +1 på hjul i
                    int up = cur + ((d == 9 ? -9 : 1) * P10[i]);
                    if (!dead[up] && !fSeen[up]) {
                        if (bSeen[up]) return steps + 1;
                        fSeen[up] = true;
                        fq[ft++] = up;
                    }

                    // -1 på hjul i
                    int dn = cur + ((d == 0 ? 9 : -1) * P10[i]);
                    if (!dead[dn] && !fSeen[dn]) {
                        if (bSeen[dn]) return steps + 1;
                        fSeen[dn] = true;
                        fq[ft++] = dn;
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    private int toInt(String s) {
        return (s.charAt(0) - '0') * 1000
             + (s.charAt(1) - '0') * 100
             + (s.charAt(2) - '0') * 10
             + (s.charAt(3) - '0');
    }
}
 */
 /**
 Explanation for the array/arithmetics version below:
 - State encoding: each 4-digit lock state is treated as an integer in [0,9999].
 - `seen[next]` is a boolean visited array for O(1) checks; deadends are pre-marked.
 - For each dequeued `curr`, we iterate each wheel place `j = 1,10,100,1000`.
     - `mask` extracts the digit at that place: `(curr % (j * 10)) / j`.
     - `masked = curr - mask * j` zeroes that digit so we can write a new one.
     - The inner `k` loop uses `k = 1` and `k += 8` to generate +1 and -1 modulo 10
         (since (d+1)%10 and (d+9)%10 are the two neighbors).
     - `next = masked + ((mask + k) % 10) * j` sets the digit to its neighbor.
 - BFS ensures minimal turns; we early-return when we hit `target`.
 umm??
 class Solution {
    public int openLock(String[] deadends, String target) {
        if (target.equals("0000")) return 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        boolean[] seen = new boolean[10000];
        for (String el : deadends)
            seen[Integer.parseInt(el)] = true;
        int targ = Integer.parseInt(target);
        if (seen[0]) return -1;
        for (int turns = 1; !queue.isEmpty(); turns++) {
            int qlen = queue.size();
            for (int i = 0; i < qlen; i++) {
                int curr = queue.poll();
                for (int j = 1; j < 10000; j *= 10) {
                    int mask = curr % (j * 10) / j,
                        masked = curr - (mask * j);
                    for (int k = 1; k < 10; k += 8) {
                        int next = masked + (mask + k) % 10 * j;
                        if (seen[next]) continue;
                        if (next == targ) return turns;
                        seen[next] = true;
                        queue.add(next);
                    }
                }
            }
        }
        return -1;
    }
}
  */