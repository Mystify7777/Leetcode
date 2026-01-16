// 131. Palindrome Partitioning
public class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = 0; i < s.length(); i++) {
            for(int j = 0; j <= i; j++) {
                if(s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[j+1][i-1])) {
                    dp[j][i] = true;
                }
            }
        }
        helper(res, new ArrayList<>(), dp, s, 0);
        return res;
    }
    
    private void helper(List<List<String>> res, List<String> path, boolean[][] dp, String s, int pos) {
        if(pos == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }
        
        for(int i = pos; i < s.length(); i++) {
            if(dp[pos][i]) {
                path.add(s.substring(pos,i+1));
                helper(res, path, dp, s, i+1);
                path.remove(path.size()-1);
            }
        }
    }
}


class Solution2 {
    int n;
    boolean[][] is_palindrome;
    String[][] substrings;

    List<List<String>> ans;

    void FindSubstrings(int ind, ArrayList<String> list) {
        if (ind == n) {
            ans.add(new ArrayList<String>(list));
            return;
        }

        for (int i = ind + 1; i <= n; i++) {
            if (!is_palindrome[ind][i]) continue;
            list.add(substrings[ind][i]);
            FindSubstrings(i, list);
            list.remove(list.size() - 1);
        }
    }

    public List<List<String>> partition(String s) {
        n = s.length();
        is_palindrome = new boolean[n + 1][n + 1];
        substrings = new String[n + 1][n + 1];
        for (int i = 0; i < n; i++) for (int j = i + 1; j <= n; j++) {
            substrings[i][j] = s.substring(i, j);
            is_palindrome[i][j] = IsPalindrome(substrings[i][j]);
        }

        ans = new ArrayList<List<String>>();
        FindSubstrings(0, new ArrayList<String>());
        return ans;
    }

    boolean IsPalindrome(String s) {
        int lower = 0;
        int higher = s.length() - 1;
        while (lower < higher) {
            if (s.charAt(lower) != s.charAt(higher)) return false;
            lower++;
            higher--;
        }
        return true;
    }
}