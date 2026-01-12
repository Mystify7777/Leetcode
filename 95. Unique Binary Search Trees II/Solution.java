/** 95. Unique Binary Search Trees II
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
    public List<TreeNode> generateTrees(int n) {
	return generateSubtrees(1, n);
}

private List<TreeNode> generateSubtrees(int s, int e) {
	List<TreeNode> res = new LinkedList<TreeNode>();
	if (s > e) {
		res.add(null); // empty tree
		return res;
	}

	for (int i = s; i <= e; ++i) {
		List<TreeNode> leftSubtrees = generateSubtrees(s, i - 1);
		List<TreeNode> rightSubtrees = generateSubtrees(i + 1, e);

		for (TreeNode left : leftSubtrees) {
			for (TreeNode right : rightSubtrees) {
				TreeNode root = new TreeNode(i);
				root.left = left;
				root.right = right;
				res.add(root);
			}
		}
	}
	return res;
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
    List<TreeNode>[][] memo = new List[10][10];
    private List<TreeNode> solve(int start, int end){
        
        if (memo[start][end] != null) {
            return memo[start][end];
        }

        List<TreeNode> li = new ArrayList<>();

        for(int i = start; i<=end; i++){
            
            List<TreeNode> leftSide = solve(start, i-1);
            List<TreeNode> rightSide = solve(i+1, end);

            for(TreeNode left: leftSide){
                for(TreeNode right: rightSide){

                    TreeNode curr = new TreeNode(i);

                    curr.left = left;
                    curr.right = right;
                    li.add(curr);
                }
            }
            
        }

        if(li.size() == 0){
            li.add(null);
            return li;
        }

        memo[start][end] = li;
        return li;
        
    }
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) return new ArrayList<>();
        return solve(1, n);
    }
}