// 1382. Balance a Binary Search Tree
// https://leetcode.com/problems/balance-a-binary-search-tree/

class Solution {
    public void inorder(TreeNode node, List<Integer> vals) {
        if (node == null) return;
        inorder(node.left, vals);
        vals.add(node.val);
        inorder(node.right, vals);
    }
    public TreeNode build(List<Integer> vals, int l, int r) {
        if (l > r) return null;
        int mid = (l + r) / 2;
        TreeNode node = new TreeNode(vals.get(mid));
        node.left  = build(vals, l, mid - 1);
        node.right = build(vals, mid + 1, r);
        return node;
    }
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        inorder(root, vals);
        return build(vals, 0, vals.size() - 1);
    }
}

/**
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
class Solution2 {
    ArrayList<TreeNode> res = new ArrayList<TreeNode>();
    public TreeNode balanceBST(TreeNode root) {
        inorder(root);
        return Build_BST(0, res.size() - 1);
    }

    public void inorder(TreeNode root ){
         if(root == null) return;
         inorder(root.left);
         res.add(root);
         inorder(root.right);
    }
    
    public TreeNode Build_BST( int start, int end){
        if(start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = res.get(mid);
        node.left = Build_BST( start, mid - 1);
        node.right = Build_BST( mid + 1, end);
        return node;
    }
}