// 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
// https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
class Solution {
   public int minPartitions(String n) {
	int max = 0;
	for (int i=0; i<n.length(); i++) {
		if (n.charAt(i) - '0' == 9) return 9;
		max = Math.max(max, (n.charAt(i) - '0'));
	}
	return max;
}
}
class Solution2 {
    public int minPartitions(String n) {
        for(int i=9;i>0;i--)
        {
            if(n.contains(i+""))
            return i;
        }
        return 0;
    }
}

class Solution3 {
    public int minPartitions(String n) {
        String str = String.valueOf(n);
        char first = str.charAt(0);

        char second = first;

        for (char c : str.toCharArray()) {
            if (c > second) second = c;
        }

        return first - '0' + (second - first > 0 ? second - first : 0); //why is this working? because the first char is the max char in the string and if there is a char greater than the first char then we need to add the difference between the second char and the first char to the first char to get the minimum number of deci-binary numbers needed to partition the string.
    }
}