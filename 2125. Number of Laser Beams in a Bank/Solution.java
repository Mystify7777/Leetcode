//2125. Number of Laser Beams in a Bank
//https://leetcode.com/problems/number-of-laser-beams-in-a-bank/
class Solution {
    public int numberOfBeams(String[] bank) {
        int prevRowCount = 0;
        int total=0;

        for(String row : bank) {
            int curRowCount = calc(row);
            if(curRowCount==0) 
                continue;

            total += curRowCount * prevRowCount;
            prevRowCount = curRowCount;
        }
        return total;
    }

    private int calc(String s) {
        int count = 0;
        for(char c : s.toCharArray()) 
            count += c - '0';

        return count;
    }    
}
//alternate approach
/**
class Solution {
    public int numberOfBeams(String[] bank) {
        int number = 0, last = mun(bank[0]);
        for (int i = 1; i < bank.length; i++) {
            int current = mun(bank[i]);
            if (current == 0) continue;
            number += (last * (last = current));
        }
        return number;
    }

    int mun(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++)
            res += (s.charAt(i) - '0');
        return res;
    }
}
 */