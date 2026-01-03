// 22. Generate Parentheses
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();

        dfs(0, 0, "", n, res);

        return res;        
    }

    private void dfs(int openP, int closeP, String s, int n, List<String> res) {
        if (openP == closeP && openP + closeP == n * 2) {
            res.add(s);
            return;
        }

        if (openP < n) {
            dfs(openP + 1, closeP, s + "(", n, res);
        }

        if (closeP < openP) {
            dfs(openP, closeP + 1, s + ")", n, res);
        }
    }    
}

//compare these approaches in how_why file
/**
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(0, 0, n, ans, new StringBuilder());
        return ans;
    }

    private void backtrack(int start, int end, int n, List<String> ans, StringBuilder sb) {
        if (start == n && end == n) {
            ans.add(sb.toString());
            return;
        }

        if (start < n) {
            sb.append("(");
            backtrack(start + 1, end, n, ans, sb);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (end < start) {
            sb.append(")");
            backtrack(start, end + 1, n, ans, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
 */