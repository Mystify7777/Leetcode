// 3121. Count the Number of Special Characters II
// https://leetcode.com/problems/count-the-number-of-special-characters-ii/

class Solution {
    public int numberOfSpecialChars(String word) {
        boolean[][] A = new boolean[2][27];

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int idx = ch & 31;
            int Case = (ch >> 5) & 1;

            A[Case][idx] = Case == 0 || !A[0][idx];
        }

        int res = 0;
        for (int i = 1; i < 27; i++)
            if (A[0][i] && A[1][i])
                res++;

        return res;
    }
}

class Solution2 {

    public int numberOfSpecialChars(String word) {
        byte[] ca = word.getBytes();
        int[] lowerIndex = new int[26];
        int[] upperIndex = new int[26];
        for(int i = 0; i<ca.length; i++) {
            int c = ca[i] & 0xFF;
            if (c > 'Z') {
                lowerIndex[c-'a'] = i+1;
            } else {
                int ind = c-'A';
                if (upperIndex[ind]==0) {
                    upperIndex[ind]= i+1;
                }
            }
        }
        int result = 0;
        for (int i = 0; i<26; i++) {
            if (lowerIndex[i]!=0 && upperIndex[i]!=0 && lowerIndex[i]<upperIndex[i]) {
                result++;
            }
        }
        return result;
    }
}
