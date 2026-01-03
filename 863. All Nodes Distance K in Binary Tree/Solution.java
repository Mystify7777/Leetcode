/** 863. All Nodes Distance K in Binary Tree
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
 //copypasted
 //explain it in simple manner with example walkthrough.
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        Map<Integer, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode top = queue.poll();

                if (top.left != null) {
                    parent.put(top.left.val, top);
                    queue.offer(top.left);
                }

                if (top.right != null) {
                    parent.put(top.right.val, top);
                    queue.offer(top.right);
                }
            }
        }

        Map<Integer, Integer> visited = new HashMap<>();
        queue.offer(target);
        while (k > 0 && !queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode top = queue.poll();

                visited.put(top.val, 1);

                if (top.left != null && !visited.containsKey(top.left.val)) {
                    queue.offer(top.left);
                }

                if (top.right != null && !visited.containsKey(top.right.val)) {
                    queue.offer(top.right);
                }

                if (parent.containsKey(top.val) && !visited.containsKey(parent.get(top.val).val)) {
                    queue.offer(parent.get(top.val));
                }
            }

            k--;
        }

        while (!queue.isEmpty()) {
            ans.add(queue.poll().val);
        }
        return ans;
    }
}

// compare this solution

/**

class Solution {
    List<Integer> ans=new LinkedList<>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        //node k
        foundAtDistance(root,target,k);
        return ans;
    }

    public void findChildrenAtKDistance(TreeNode root, int distance){
        if(root==null) return;
        if(distance<0)return;
        if(distance==0){
            ans.add(root.val);
            return;
        }
        else{
            findChildrenAtKDistance(root.left,distance-1);
            findChildrenAtKDistance(root.right,distance-1);
        }
    }

    public int foundAtDistance(TreeNode root,TreeNode target,int k){
        if(root==null){
            return 0;
        }

        if(root.val==target.val){
            findChildrenAtKDistance(root,k);
            return 1;
        }

        int leftDistance=foundAtDistance(root.left,target,k);
        int rightDistance=foundAtDistance(root.right,target,k);

        if(leftDistance>0){
            if(leftDistance>k){
                return 0;
            }else if(leftDistance==k){
                ans.add(root.val);
                return 0;
            }
            else{
                findChildrenAtKDistance(root.right, k-leftDistance-1);
                return leftDistance+1;
            }
        }else if(rightDistance>0){
            if(rightDistance>k){
                return 0;
            }else if(rightDistance==k){
                ans.add(root.val);
                return 0;
            }
            else{
                findChildrenAtKDistance(root.left, k-rightDistance-1);
                return rightDistance+1;
            }
        }else{
            return 0;
        }
    }
}
 */