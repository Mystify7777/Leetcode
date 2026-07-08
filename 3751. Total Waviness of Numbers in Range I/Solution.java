// 3751. Total Waviness of Numbers in Range I
// https://leetcode.com/problems/total-waviness-of-numbers-in-range-i/
class Solution {
    public int totalWaviness(int num1, int num2) {
        return (int) (helper(num2) - helper(num1 - 1));
    }

    private long helper(long num) {
        long result = 0L;
        for (int pow10 = 1; num >= pow10 * 100; pow10 *= 10) {
            long maxPrefix = num / (pow10 * 1000);
            long tmp = num / pow10;
            int left = (int) (tmp / 100 % 10);
            int mid = (int) (tmp / 10 % 10);
            int right = (int) (tmp % 10);
            long count = maxPrefix * 570 - 45;
            count += (121 + left * 15 - left * left) * left / 3;
            count += (left + mid) * Math.max(mid - left - 1, 0) / 2;
            count += (19 - Math.min(left, mid)) * Math.min(left, mid) / 2;
            if (left < mid) {
                count += Math.min(mid, right);
            } else if (left > mid) {
                count += Math.max(right - mid -1, 0);
            }
            result += count * pow10;
            if ((left - mid) * (mid - right) < 0) {
                long maxSuffix = num % pow10;
                result += maxSuffix + 1;
            }
        }
        return result;
    }
}

class Solution2 {
    public int totalWaviness(int num1, int num2) {
        int totalWaviness = 0;
        
        for (int i = num1; i <= num2; i++) {
            totalWaviness += getWaviness(i);
        }
        
        return totalWaviness;
    }
    
    private int getWaviness(int num) {
        String s = Integer.toString(num);
        if (s.length() < 3) {
            return 0;
        }
        
        int waviness = 0;
        // Check only middle digits (exclude first and last)
        for (int i = 1; i < s.length() - 1; i++) {
            char prev = s.charAt(i - 1);
            char curr = s.charAt(i);
            char next = s.charAt(i + 1);
            
            // Check for a Peak
            if (curr > prev && curr > next) {
                waviness++;
            }
            // Check for a Valley
            else if (curr < prev && curr < next) {
                waviness++;
            }
        }
        
        return waviness;
    }
}