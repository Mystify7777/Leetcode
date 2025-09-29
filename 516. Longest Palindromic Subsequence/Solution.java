//copypaste
//516. Longest Palindromic Subsequence
class Solution {
    public int longestPalindromeSubseq(String s) {
        // Get the length of the input string
        int n = s.length();
        // Initialize a 2D array to store the length of the longest palindromic subsequence
        int[][] dp = new int[n][n];
        
        // Iterate over the substrings in reverse order to fill in the dp table bottom-up
        for (int i = n-1; i >= 0; i--) {
            // Base case: the longest palindromic subsequence of a single character is 1
            dp[i][i] = 1;
            for (int j = i+1; j < n; j++) {
                // If the two characters match, the longest palindromic subsequence can be extended by two
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i+1][j-1];
                } else {
                    // Otherwise, we take the maximum of the two possible substrings
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        
        // The length of the longest palindromic subsequence is in the top-right corner of the dp table
        return dp[0][n-1];
    }
}
/**
class Solution {
    
    private int helper(String s, int left, int right, int [][] cache){
        if(left==right)
            return 1;
        
        if(left>right)
            return 0;
        
        if(cache[left][right]!=-1)
            return cache[left][right];
        
        if(s.charAt(left) == s.charAt(right)){
            cache[left][right] = 2 + helper(s, left+1, right-1, cache);
            return cache[left][right];
        }
        
        int leftIncrement = helper(s,left+1,right, cache);
        int rightIncrement =  helper(s,left,right-1, cache);                          
        cache[left][right] = Math.max(leftIncrement, rightIncrement);
        return cache[left][right];
        
        
    }
    
    
    public int longestPalindromeSubseq(String s) {
        /* If the two ends of a string are the same, then they must be included in the longest palindrome subsequence. Otherwise, both ends cannot be included in the longest palindrome subsequence. 
        
        int size = s.length();
        if(size==0 || s==null)
            return 0;
        int [][] dp = new int[size][size];
        for (int[] row : dp) 
            Arrays.fill(row, -1);
        return helper(s, 0, s.length()-1,dp);
    }
} */

/**
class Solution {
    public int lcs(String s){
        int n=s.length();
        int [][]dp=new int[n+1][n+1];
        StringBuilder sb=new StringBuilder(s);
        String s2=sb.reverse().toString();
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(s.charAt(i-1)==s2.charAt(j-1)){
              dp[i][j]=1+dp[i-1][j-1];
        }
         else dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
            }
        }
        return dp[n][n];
    }
    public int longestPalindromeSubseq(String s) {
       Callable<Integer>callable=()->lcs(s);
       FutureTask<Integer>future=new FutureTask<>(callable);
       new Thread(future).start();
       try{
        return future.get();
       }catch(Exception e){
        return 0;
       }
    }
}
 */