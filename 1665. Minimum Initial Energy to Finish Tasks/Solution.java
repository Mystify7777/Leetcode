// 1665. Minimum Initial Energy to Finish Tasks
// https://leetcode.com/problems/minimum-initial-energy-to-finish-tasks/
import java.util.*;

class Solution1 {
    public int minimumEffort(int[][] shop) {
        Arrays.sort(shop, (a, b) -> b[1] - b[0] - (a[1] - a[0]));

        int start = shop[0][1];
        int bal = shop[0][1] - shop[0][0];
        int loan = 0;

        for (int i = 1; i < shop.length; i++) {
            int cost = shop[i][0];
            int thresh = shop[i][1];

            if (bal < thresh) {
                loan += thresh - bal;
                bal = thresh;
            }

            bal -= cost;
        }

        return start + loan;
    }
}

class Solution2 {
    public int minimumEffort(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> a[1] - a[0] - (b[1] - b[0]));
        int ans = 0;
        for (int[] task : tasks) {
            ans = Math.max(ans + task[0], task[1]);
        }
        return ans;
    }
}

public class Solution {
    public int minimumEffort(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));

        int energy = 0;
        int curr = 0;

        for (int[] task : tasks) {
            if (curr < task[1]) {
                energy += task[1] - curr;
                curr = task[1];
            }

            curr -= task[0];
        }

        return energy;
    }
}
