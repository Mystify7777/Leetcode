// 1513. Number of Substrings With Only 1s
//https://leetcode.com/problems/number-of-substrings-with-only-1s/
class Solution {
    public int numSub(String s) {
        long cnt = 0, total = 0, mod = 1000000007;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                cnt++;
            } else {
                cnt = 0;
            }
            total = (total + cnt) % mod;
        }
        return (int) total;
    }
}

//slightly faster approach
/**
class Solution {
    public int numSub(String s) {
		char[] chars = s.toCharArray();
		long ans = 0, count = 0;
		for (char c : chars) {
			if (c == '1') {
				count++;
			} else {
				ans += count * (count + 1) / 2;
				count = 0;
			}
		}
		ans += count * (count + 1) / 2;
		return (int) (ans % 1000000007);
	}
}
 */