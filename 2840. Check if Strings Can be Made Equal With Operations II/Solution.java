// 2840. Check if Strings Can be Made Equal With Operations II
// https://leetcode.com/problems/check-if-strings-can-be-made-equal-with-operations-ii/
class Solution {
    public boolean checkStrings(String s1, String s2) {
        int[] freq = new int[52];

        for (int i = 0; i < s1.length(); i++) {
            int off = (i & 1) * 26;
            freq[s1.charAt(i) - 'a' + off]++;
            freq[s2.charAt(i) - 'a' + off]--;
        }

        for (int i = 0; i < 52; i++)
            if (freq[i] != 0) return false;

        return true;
    }
}

class Solution2 {
    public boolean checkStrings(String s1, String s2) {
        int  n = s1.length();
        int freq1[]=new int[26];
        for(int i=0;i<n;i+=2){
            freq1[s1.charAt(i)-'a']++;
        }
        int freq2[]=new int[26];
        for(int i=0;i<n;i+=2){
            freq2[s2.charAt(i)-'a']++;
        }
        for(int i =0;i<26;i++){
            if(freq1[i]!=freq2[i])return false;
            freq1[i]=0;
            freq2[i]=0;
        }
        for(int i=1;i<n;i+=2){
            freq1[s1.charAt(i)-'a']++;
        }
        for(int i=1;i<n;i+=2){
            freq2[s2.charAt(i)-'a']++;
        }
        for(int i =0;i<26;i++){
            if(freq1[i]!=freq2[i])return false;
        }
        return true;
    }
}