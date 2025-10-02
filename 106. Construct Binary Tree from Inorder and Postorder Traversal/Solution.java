//copypasted
//106. Construct Binary Tree from Inorder and Postorder Traversal
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


// class Solution {
//     public TreeNode buildTree(int[] inorder, int[] postorder) {
        
//     }
// }
import java.util.*;
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // Call the recursive function with full arrays and return the result
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    
    private TreeNode buildTree(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        // Base case
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }
        
        // Find the root node from the last element of postorder traversal
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);
        
        // Find the index of the root node in inorder traversal
        int rootIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                rootIndex = i;
                break;
            }
        }
        
        // Recursively build the left and right subtrees
        int leftSize = rootIndex - inStart;
        int rightSize = inEnd - rootIndex;
        root.left = buildTree(inorder, inStart, rootIndex - 1, postorder, postStart, postStart + leftSize - 1);
        root.right = buildTree(inorder, rootIndex + 1, inEnd, postorder, postEnd - rightSize, postEnd - 1);
        
        return root;
    }
}

/**

class Solution {
    int i, o; // i: inorder traversal idx, o: postorder traversal idx;
    public TreeNode buildTree(int[] in, int[] po) {
        i = o = po.length - 1;
        return dfs(in, po, 3001);
    }
    
    private TreeNode dfs(int[] in, int[] po, int leftBoundary) {
        if (o == -1 || in[i] == leftBoundary) return null;
        TreeNode node = new TreeNode(po[o--]);
        node.right = dfs(in, po, node.val);
        i--;
        node.left  = dfs(in, po, leftBoundary);
        return node;
    }
} */

/**
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
 *//* 
class Solution {
    private int idx;
    private TreeNode helper(int post[],int in[],int l,int r){
        if(l > r) return null;
        TreeNode root = new TreeNode(post[idx]);
        int m = l;
        for(int i = r;i >= l;i--){
            if(in[i] == post[idx]){
                m = i;
                break;
            }
        }
        idx--;
        root.right = helper(post,in,m + 1,r);
        root.left = helper(post,in,l,m - 1);
        return root;
    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        idx = inorder.length - 1;
        return helper(postorder,inorder,0,inorder.length - 1);
    }
}
 */