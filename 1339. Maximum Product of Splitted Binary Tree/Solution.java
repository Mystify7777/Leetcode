/** 1339. Maximum Product of Splitted Binary Tree
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    long MOD = 1000000007L;
    long ans = 0;

    private long dfs(TreeNode node) {
        if (node == null) return 0;

        node.val += dfs(node.left) + dfs(node.right);
        return node.val;
    }

    public int maxProduct(TreeNode root) {
        long total = dfs(root);

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) continue;

            long cur = (total - node.val) * node.val;
            ans = Math.max(ans, cur);

            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
        }

        return (int)(ans % MOD);
    }
}



//  why is second implementation fats even though it is same?
class Solution2 {
    static long sum;
    static long max;
    static int MODULO = 1000000007;
    // static long MODULO = 1000000007;
    private static long getSum(TreeNode root){
        if(root == null){
            return 0;
        }
        return root.val + getSum(root.left) + getSum(root.right);
    }
    private static long getMaxProduct(TreeNode root){
        if(root == null){
            return 0;
        }
        long left = getMaxProduct(root.left);
        long right = getMaxProduct(root.right);

        long t1 = left + right + root.val;
        long temp = (sum-t1) * t1;
        if(temp > max){
            max = temp;
        }
        return t1;
    }
    public static int maxProduct(TreeNode root) {
        max = 0;
        sum = getSum(root);
        getMaxProduct(root);
        return (int)(max%MODULO);
    }
}