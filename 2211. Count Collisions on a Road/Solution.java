// 2211. Count Collisions on a Road
// https://leetcode.com/problems/count-collisions-on-a-road/
class Solution {
    public int countCollisions(String dir) {
        
        int res = 0, n = dir.length(), i = 0, carsFromRight = 0;
        
        while (i < n && dir.charAt(i) == 'L') i++;
        
        for ( ; i<n; i++) {
            if (dir.charAt(i) == 'R')  carsFromRight++;
            else {
                res += (dir.charAt(i) == 'S') ? carsFromRight : carsFromRight+1;
                carsFromRight = 0;
            }
        }
        return res;
    }
}
/**
class Solution {
    public int countCollisions(String str) {
        int count=0;
        int left=0,right=str.length()-1;
        char[] s=str.toCharArray();
        while(left<s.length && s[left]=='L'){
            left++;
        }
        while(right>=0 && s[right]=='R'){
            right--;
        }
        for(int i=left;i<=right;i++){
            if(s[i]!='S') count++;
        }
        return count;
    }
}
 */