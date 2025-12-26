// 2483. Minimum Penalty for a Shop
// https://leetcode.com/problems/minimum-penalty-for-a-shop/

public class Solution {
    public int bestClosingTime(String customers) {
        int max_score = 0, score = 0, best_hour = -1;
        for(int i = 0; i < customers.length(); ++i) {
            score += (customers.charAt(i) == 'Y') ? 1 : -1;
            if(score > max_score) {
                max_score = score;
                best_hour = i;
            }
        }
        return best_hour + 1;
    }
}

//what's happening in this code
/**
class Solution {
    public int bestClosingTime(String customers) {
        byte[] cs = customers.getBytes(java.nio.charset.Charset.forName("ISO-8859-1"));
        int bestTime = -1;
        int customersLeft = 0;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == 89) {
                customersLeft++;
                if (customersLeft > 0) {
                    bestTime = i;
                    customersLeft = 0;
                }
            } else {
                customersLeft--;
            }
        }

        return bestTime+1;
    }
}

 */