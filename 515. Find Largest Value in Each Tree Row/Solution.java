// 515. Find Largest Value in Each Tree Row
// https://leetcode.com/problems/find-largest-value-in-each-tree-row/
class Solution {
    List<Integer> ans;
    
    public List<Integer> largestValues(TreeNode root) {
        ans = new ArrayList<Integer>();
        dfs(root, 0);
        return ans;
    }
    
    public void dfs(TreeNode node, int depth) {
        if (node == null) {
            return;
        }
        
        if (depth == ans.size()) {
            ans.add(node.val);
        } else {
            ans.set(depth, Math.max(ans.get(depth), node.val));
        }
        
        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }
}