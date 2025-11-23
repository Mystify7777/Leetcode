// 1262. Greatest Sum Divisible by Three
// https://leetcode.com/problems/greatest-sum-divisible-by-three/
class Solution {
    public int maxSumDivThree(int[] nums) {
        int sum = 0;

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int min11 = Integer.MAX_VALUE;
        int min22 = Integer.MAX_VALUE;

        for (int x : nums) {
            sum += x;
            int r = x % 3;

            if (r == 1) {
                if (x < min1) { min11 = min1; min1 = x; }
                else if (x < min11) min11 = x;
            } 
            else if (r == 2) {
                if (x < min2) { min22 = min2; min2 = x; }
                else if (x < min22) min22 = x;
            }
        }

        int rem = sum % 3;

        if (rem == 0) return sum;

        if (rem == 1) {
            int remove1 = min1;
            int remove2 = (min2 == Integer.MAX_VALUE || min22 == Integer.MAX_VALUE)
                            ? Integer.MAX_VALUE : min2 + min22;
            int remove = Math.min(remove1, remove2);
            return (remove == Integer.MAX_VALUE) ? 0 : sum - remove;
        } 
        else {
            int remove1 = min2;
            int remove2 = (min1 == Integer.MAX_VALUE || min11 == Integer.MAX_VALUE)
                            ? Integer.MAX_VALUE : min1 + min11;
            int remove = Math.min(remove1, remove2);
            return (remove == Integer.MAX_VALUE) ? 0 : sum - remove;
        }
    }
}

/**
alternate approach
class Solution {
    public int maxSumDivThree(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 3 == 0) return sum;
        else if (sum % 3 == 1) {
            // case 1, remove smallest %= 1 or 2 %= 2
            TwoS mod2 = new TwoS();
            int mod1 = Integer.MAX_VALUE;
            for (int num : nums) {
                if (num % 3 == 2) mod2.add(num);
                else if (num % 3 == 1) mod1 = Math.min(mod1, num);
            }
            return Math.max(0, Math.max(sum - mod2.getSum(), sum - mod1));
        } 
        else {
            // case 2 remove 2 smallest %= 1 or % one smallest %= 2
            TwoS mod1 = new TwoS();
            int mod2 = Integer.MAX_VALUE;
            for (int num : nums) {
                if (num % 3 == 2) mod2 = Math.min(mod2, num);
                else if (num % 3 == 1) mod1.add(num);
            }
            return Math.max(0, Math.max(sum - mod1.getSum(), sum - mod2));
        }
    }
    
    public static class TwoS {
        int a;
        int b;
        
        public TwoS() {
            a = Integer.MAX_VALUE;
            b = Integer.MAX_VALUE;
        }
        
        public void add(int num) {
            if (num <= a) {
                b = a;
                a = num;
            } else if (num < b) {
                b = num;
            }
        }
        
        public int getSum() {
            if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) return Integer.MAX_VALUE;
            return a + b;
        }
    }
}
 */