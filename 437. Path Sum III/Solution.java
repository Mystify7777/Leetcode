/** 437. Path Sum III
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
    public int pathSum(TreeNode root, int targetSum) {
         if(root == null) return 0;
    return pathSumFromRoot(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
}

private static int pathSumFromRoot(TreeNode root, long sum){
    if(root == null) return 0;
    
    int res = 0;
    if(root.val == sum) {
        res += 1;
    }
    
    res += pathSumFromRoot(root.left, sum - root.val);
    res += pathSumFromRoot(root.right, sum - root.val);
    
    return (int)res;

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

    private void solve(TreeNode root, int targetSum, Long prefixSum,Map<Long,Integer>map,int[]ans ){

        if(root==null)
        return ;

        prefixSum+=root.val;
        
        Long remaining=prefixSum-targetSum;

        ans[0]+=map.getOrDefault(remaining,0);
        map.put(prefixSum,map.getOrDefault(prefixSum,0)+1);
        solve(root.left,targetSum,prefixSum,map,ans);
        solve(root.right,targetSum,prefixSum,map,ans);

        //remove current node data before moving to the right child side of parent
        map.put(prefixSum,map.get(prefixSum)-1);
       
       
        



    }
    public int pathSum(TreeNode root, int targetSum) {
        int[]ans=new int[1];
        Long prefixSum=0L;
        Map<Long,Integer>map= new HashMap<>();
        map.put(0L,1);

        solve(root,targetSum,prefixSum,map,ans);
        return ans[0];
        
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

//time: O(n) each node visited once
//space: map O(n)
class Solution3 {
    public int pathSum(TreeNode root, int targetSum) {
        // Map stores: prefixSum -> frequency
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1);  // Base case: sum = 0 has frequency 1
        return dfs(root, 0L, targetSum, prefixSumMap);
    }
    
    private int dfs(TreeNode node, long currentSum, int target, Map<Long, Integer> prefixSumMap) {
        if (node == null) return 0;
        
        // Update current prefix sum
        currentSum += node.val;
        
        // Check if (currentSum - target) exists in map
        // If yes, that means there's a subpath summing to target
        int count = prefixSumMap.getOrDefault(currentSum - target, 0);
        
        // Add current prefix sum to map
        prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        // Recurse on children
        count += dfs(node.left, currentSum, target, prefixSumMap);
        count += dfs(node.right, currentSum, target, prefixSumMap);
        
        // Backtrack: remove current prefix sum from map
        prefixSumMap.put(currentSum, prefixSumMap.get(currentSum) - 1);
        if (prefixSumMap.get(currentSum) == 0) {
            prefixSumMap.remove(currentSum);
        }
        
        return count;
    }
}


// //brute force: time O(N^2) -- skewed tree
// //starts DFS from every node
// //space O(h), h: height of tree (balanced: h = logn, worst h = n)
// // maximum recursion stack depth when traversing from root to leaf. (calling countFrom)
// class Solution {
//     public int pathSum(TreeNode root, int targetSum) {
//         if (root == null) return 0;

//         //start from root node -- go through each node O(n)
//         int countRoot = countFrom(root, targetSum);

//         //count left subtree -- recurse the children O(n)
//         int countLeft = pathSum(root.left, targetSum);
//         //count right subtree
//         int countRight = pathSum(root.right, targetSum);

//         return countRoot + countLeft + countRight;
//     }

//     private int countFrom(TreeNode node, long targetSum) {
//         if (node == null) return 0;

//         int count = 0;
//         if (node.val == targetSum) count++;

//         count += countFrom(node.left, targetSum - node.val);
//         count += countFrom(node.right, targetSum - node.val);

//         return count;
//     }
// }
