// 1716. Calculate Money in Leetcode Bank
//https://leetcode.com/problems/calculate-money-in-leetcode-bank/
class Solution {
    public int totalMoney(int n) {
        int week_count = n / 7;
        int remaining_days = n % 7;
        
        int total = ((week_count * (week_count - 1)) / 2) * 7; // Contribution from complete weeks
        total += week_count * 28; // Contribution from complete weeks (additional on Mondays)
        total += ((remaining_days * (remaining_days + 1)) / 2) + (week_count * remaining_days); // Contribution from remaining days
        
        return total;
    }
}