// 173. Binary Search Tree Iterator


//  * Definition for a binary tree node.

import java.util.Stack;


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
 */
public class BSTIterator {
    private Stack<TreeNode> stack = new Stack<TreeNode>();
    
    public BSTIterator(TreeNode root) {
        pushAll(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode tmpNode = stack.pop();
        pushAll(tmpNode.right);
        return tmpNode.val;
    }
    
    private void pushAll(TreeNode node) {
        for (; node != null; stack.push(node), node = node.left);
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */

 /**
 
  import java.util.Stack;

class BSTIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
 */

 /**
 
 class BSTIterator {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    private ListNode head = new ListNode(-1);
    private ListNode curr = head;

    public BSTIterator(TreeNode root) {
        inorder(root);
    }

    public void inorder(TreeNode root){
        if(root == null) return;

        inorder(root.left);
        this.curr.next = new ListNode(root.val);
        this.curr = this.curr.next;
        inorder(root.right);

    }
    
    public int next() {
        int curr_int = this.head.next.val;
        this.head = this.head.next;
        return curr_int;
    }
    
    public boolean hasNext() {
        if(this.head.next != null){
            return true;
        }else{
            return false;
        }
    }
}
 */