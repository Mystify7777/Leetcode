/** 98. Validate Binary Search Tree
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
    public boolean isValidBST(TreeNode root) {
   if (root == null) return true;
   Stack<TreeNode> stack = new Stack<>();
   TreeNode pre = null;
   while (root != null || !stack.isEmpty()) {
      while (root != null) {
         stack.push(root);
         root = root.left;
      }
      root = stack.pop();
      if(pre != null && root.val <= pre.val) return false;
      pre = root;
      root = root.right;
   }
   return true;
}
}

//compare
/**
class Solution {
    public boolean isValidBST(TreeNode root) {
        return  isvalid (root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public boolean  isvalid(TreeNode root,long minval,long max){
        if (root==null)
            return true ;
        
        if(root.val>=max||root.val<=minval)
        return false;
        return isvalid (root.left,minval,root.val)&& isvalid(root.right,root.val,max);
    } 
}
 */