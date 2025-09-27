// 322. Coin Change
/*
class Solution {
	// public int coinChange(int[] coins, int amount) {
    //     if(amount<0) return -1;
    //     if(amount==0) return 0;
    //     int cc=-1;
    //     for(int i=0;i<coins.length;i++) {
    //         int coin=coinChange(coins, amount-coins[i]);
    //         if(coin>=0) cc=cc<0?coin+1:Math.min(cc,coin+1);
    //     }
    //     return cc;
    // }

    // 	public int coinChange(int[] coins, int amount) {
    //     Map<Integer,Integer> map=new HashMap<>();
    //     return coinChange(coins,amount,map);
    // }
    // private int coinChange(int[] coins, int amount, Map<Integer,Integer> mem ){
    //     if(amount<0) return -1;
    //     if(amount==0) return 0;
    //     Integer c=mem.get(amount);
    //     if(c!=null) return c;
    //     int cc=-1;
    //     for(int i=0;i<coins.length;i++) {
    //         int coin=coinChange(coins, amount-coins[i],mem);
    //         if(coin>=0) cc=cc<0?coin+1:Math.min(cc,coin+1);
    //     }
    //     mem.put(amount,cc);
    //     return cc;
    // }

    // 	public int coinChange(int[] coins, int amount) {
    //     int[] dp=new int[amount+1];
    //     Arrays.fill(dp,amount+1);
    //     dp[0]=0;
    //     for(int i=1;i<=amount;i++)
    //         for(int c:coins)
    //             if(i>=c) dp[i]=Math.min(dp[i],dp[i-c]+1);
    //     return dp[amount]>amount?-1:dp[amount];
    // }

    // 	public int coinChange(int[] coins, int amount) {
    //     Queue<Integer> q = new LinkedList<>();
    //     q.add(0);
    //     int cs = 0;
    //     boolean[] vstd = new boolean[amount+1];
    //     while(!q.isEmpty()) {
    //         int n = q.size();
    //         for(int i=0;i<n;i++) {
    //             int sum = q.poll();
    //             if(sum==amount) {
    //                 return cs;
    //             }
    //             if(sum>amount || vstd[sum]) {
    //                 continue;
    //             }
    //             vstd[sum]=true;
    //             for(int coin:coins) {
    //                 q.add(sum+coin);
    //             }
    //         }
    //         cs++;
    //     }
    //     return -1;
    // }

    // 	public int coinChange(int[] coins, int amount) {
    //     Set<Integer> small = new HashSet<>(), large = new HashSet<>();
    //     small.add(0);
    //     large.add(amount);
    //     int steps = 0;
    //     boolean[] vstd = new boolean[amount+1];
    //     boolean isBegin = true;
    //     while(!small.isEmpty() && !large.isEmpty()) {
    //         if(small.size()>large.size()) {
    //             Set<Integer> temp = small;
    //             small = large;
    //             large = temp;
    //             isBegin = !isBegin;
    //         }
    //         Set<Integer> nxt = new HashSet<>();
    //         for(int value:small) {
    //             if(large.contains(value)) {
    //                 return steps;
    //             }
    //             if(value <0 || value > amount || vstd[value]) {
    //                 continue;
    //             }
    //             vstd[value]=true;
    //             for(int coin:coins) {
    //                 nxt.add(isBegin?value+coin:value-coin);
    //             }
    //         }
    //         small = nxt;
    //         steps++;
    //     }
    //     return -1;
    // }

    
}

*/
import java.util.*;

public class Solution {
    public static int coinChange(int[] coins, int target) {
        if (target == 0)
            return 0;

        int n = coins.length;
        if (n == 1)
            return target % coins[0] == 0 ? target / coins[0] : -1;

        Arrays.sort(coins);

        int minCoin = coins[0];
        if (target == minCoin)
            return 1;
        int idx = 1, gcdVal = minCoin;
        while (idx < n && target >= coins[idx]) {
            if (target == coins[idx])
                return 1;
            gcdVal = gcd(coins[idx], gcdVal);
            coins[idx] -= minCoin;
            idx++;
        }
        if (target % gcdVal != 0)
            return -1;

        int minVal = (target - 1) / (coins[idx - 1] + minCoin) + 1;
        int maxVal = target / minCoin;
        for (int i = minVal; i <= maxVal; i++) {
            if (findCombination(coins, 1, idx - 1, target - i * minCoin, i))
                return i;
        }
        return -1;
    }

    private static boolean findCombination(int[] coins, int left, int right, int target, int maxCoins) {
        if (target == 0)
            return true;
        if (target < coins[left] || target / coins[right] > maxCoins)
            return false;
        if (target % coins[right] == 0)
            return true;
        if (left == right)
            return false;
        for (int k = target / coins[right] + 1; k-- > 0; ) {
            if (findCombination(coins, left, right - 1, target - k * coins[right], maxCoins - k))
                return true;
        }
        return false;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}