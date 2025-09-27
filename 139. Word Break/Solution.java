// 139. Word Break
import java.util.List;
class Solution {
   boolean[] memo;
   public boolean wordBreak(String s, List<String> wordDict) {
       memo = new boolean[s.length() + 1];
       return wordBreak(s, wordDict, 0);
   }
   boolean wordBreak(String s, List<String> wordDict, int k) {
       if (k == s.length())
           return true;
       
       if (memo[k])
           return false;
       
       for (String word : wordDict) {
           if (s.startsWith(word, k)) {
               memo[k] = true;
               if(wordBreak(s, wordDict, k + word.length())) return true;
           }
       }    
       return false;
   }
}
/**
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(s, wordDict, 0, new Boolean[s.length()]);
    }

    public boolean helper(String s, List<String> wordDict, int start, Boolean[] dp) {
        if (start == s.length())
            return true;
        
        if (dp[start] != null)
            return dp[start];
        
        for (String word : wordDict) {
            if (s.startsWith(word, start) && helper(s, wordDict, start + word.length(), dp)) {
                dp[start] = true;
                return true;
            }
        }

        dp[start] = false;
        
        return false;
    }
}
 */