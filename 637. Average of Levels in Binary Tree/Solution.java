/**637. Average of Levels in Binary Tree
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
    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>(List.of(root));
        List<Double> ans = new ArrayList<>();
        while (q.size() > 0) {
            double qlen = q.size(), row = 0;
            for (int i = 0; i < qlen; i++) {
                TreeNode curr = q.poll();
                row += curr.val;
                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
            }
            ans.add(row/qlen);
        }
        return ans;
    }
}

/**

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        return averageOfLevelDfs(root);
    }

    private List<Double> averageOfLevelsBfs(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<Double> answer = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            double total = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                    total += node.val;
                }
            }
            answer.add(total / size);
        }

        return answer;
    }

    private List<Double> averageOfLevelDfs(TreeNode root) {
        List<Double> result = new ArrayList<>();
        List<Integer> nodesAtLevel = new ArrayList<>();
        average(root, 0, result, nodesAtLevel);
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i) / nodesAtLevel.get(i));
        }

        return result;
    }

    private void average(TreeNode node, int level, List<Double> sum, List<Integer> count) {
        if (node == null) {
            return;
        }

        if (level < sum.size()) {
            sum.set(level, sum.get(level) + node.val);
            count.set(level, count.get(level) + 1);
        } else {
            sum.add(1.0 * node.val);
            count.add(1);
        }

        average(node.left, level + 1, sum, count);
        average(node.right, level + 1, sum, count);
    }
}
 */