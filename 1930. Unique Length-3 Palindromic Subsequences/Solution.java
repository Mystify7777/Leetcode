// 1930. Unique Length-3 Palindromic Subsequences
//https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
class Pair{
    int firstOcc;
    int secondOcc;
    Pair(int firstOcc, int secondOcc ){
        this.firstOcc=firstOcc;
        this.secondOcc=secondOcc;
    }
}
class Solution {
    public int countPalindromicSubsequence(String s) {
        Pair arr[] = new Pair[26];
        for (int i = 0; i < 26; i++) {
            arr[i] = new Pair(-1,-1);
        }
        for(int i=0;i<s.length();i++){
            if(arr[s.charAt(i)-'a'].firstOcc==-1){
                arr[s.charAt(i)-'a'].firstOcc = i;
            }
            else
                arr[s.charAt(i)-'a'].secondOcc=i;
        }
        int cnt=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i].firstOcc !=-1 && arr[i].secondOcc !=-1){
                HashSet<Character> hs= new HashSet<>();
                for(int j=arr[i].firstOcc+1;j<arr[i].secondOcc;j++){
                    hs.add(s.charAt(j));
                }
                cnt+=hs.size();
            }
        }
        return cnt;
    }
}
//another approach
/**
class Solution {
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        int[] charFirst = new int[26];
        int[] posBit = new int[n];
        Arrays.fill(charFirst, -1);

        int bits = 0;
        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'a';
            posBit[i] = bits |= 1 << idx;
            if (charFirst[idx] == -1) {
                charFirst[idx] = i;
                bits = 0;
            }
        }

        int res = 0;
        for (int i = n - 1; i >= 2; i--) {
            int idx = s.charAt(i) - 'a';
            if (charFirst[idx] <= -1 || charFirst[idx] == i) {
                continue;
            }
            res += Integer.bitCount(arrOr(charFirst[idx] + 1, i - 1, posBit));
            charFirst[idx] = -2;
        }
        return res;
    }

    private int arrOr(int start, int end, int[] arr) {
        int res = 0;
        for (int i=start; i<=end; i++) {
            res |= arr[i];
        }
        return res;
    }

    // public int countPalindromicSubsequence(String s) {
    //     int n = s.length();
    //     int[] charFirst = new int[26];    
    //     Arrays.fill(charFirst, -1);

    //     for (int i = 0; i < n; i++) {
    //         int idx = s.charAt(i) - 'a';
    //         if (charFirst[idx] == -1) {
    //             charFirst[idx] = i;
    //         }
    //     }

    //     int res = 0;
    //     for (int i = n - 1; i >= 2; i--) {
    //         int idx = s.charAt(i) - 'a';
    //         if (charFirst[idx] <= -1 || charFirst[idx] == i) {
    //             continue;
    //         }
    //         res += s.substring(charFirst[idx] + 1, i).chars().distinct().count();
    //         charFirst[idx] = -2;
    //     }
    //     return res;
    // }

}
 */