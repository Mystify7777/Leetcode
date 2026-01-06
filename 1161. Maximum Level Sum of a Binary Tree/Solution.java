/** 1161. Maximum Level Sum of a Binary Tree
 * https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/
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
        public int maxLevelSum(TreeNode root) {
        int max = Integer.MIN_VALUE, maxLevel = 1;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        for (int level = 1; !q.isEmpty(); ++level) {
            int sum = 0;
            for (int sz = q.size(); sz > 0; --sz) {
                TreeNode n = q.poll();
                sum += n.val;
                if (n.left != null) { 
                    q.offer(n.left);
                }
                if (n.right != null) {
                    q.offer(n.right);
                }
            }
            if (max < sum) {
                max = sum;
                maxLevel = level;
            }
        }
        return maxLevel;
    }
}

/** dfs approach
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
 
class Solution {
    int maxLevel=0;
    int currLevel=0;
    public int maxLevelSum(TreeNode root) {
        // List<List<Integer>> ans=new ArrayList<>();
        // if(root==null){
        //     return 1;
        // }
        // int level=1,sumLevel=1;;

        // Queue<TreeNode> queue=new LinkedList<>();
        // queue.add(root);
        // int max=Integer.MIN_VALUE;
        // while(!queue.isEmpty()){
        //     int size=queue.size();
        //     List<Integer> list=new ArrayList<>();
        //     int sum=0;

        //     for(int i=0;i<size;i++){
        //         TreeNode node=queue.remove();
        //         list.add(node.val);
        //         sum+=node.val;
        //         if(node.left!=null){
        //             queue.add(node.left);
        //         }
        //         if(node.right!=null){
        //             queue.add(node.right);
        //         }

                
        //     }
        //     if(sum>max){
        //         max=sum;
        //         sumLevel=level;
        //     }
        //     ans.add(list);
        //     level++;
        // }
        // return sumLevel;

        int level=0;
        int sum[] = new int[10000];
 
        dfs(root,0,sum);
        int max=Integer.MIN_VALUE;
        for(int i=0;i<=maxLevel;i++){
            if(sum[i]>max){
                max=sum[i];
                level=i;
            }
        }
        return level+1;



    }
    void dfs(TreeNode root,int currLevel,int[]  sum){
        if(root==null){
            return;
        }
        sum[currLevel]+=root.val;
        maxLevel=Math.max(maxLevel,currLevel);
        dfs(root.left,currLevel+1,sum);
        dfs(root.right,currLevel+1,sum);
    }
}
*/