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
//  2196. Create Binary Tree From Descriptions
// https://leetcode.com/problems/create-binary-tree-from-descriptions/

class Solution {
    public TreeNode createBinaryTree(int[][] descriptions) {
        TreeNode[] nodes = new TreeNode[100001];
        // for (int[] row: descriptions) {
        //     nodes[row[0]] = null;
        // }
        
        for (int[] row: descriptions) {
            nodes[row[1]] = new TreeNode(row[1]);
        }

        TreeNode root = null;

        for (int[] row: descriptions) {
            if (nodes[row[0]] == null) {
                root = nodes[row[0]] = new TreeNode(row[0]);
            }

            if (row[2] == 1) {
                nodes[row[0]].left = nodes[row[1]];
            } else {
                nodes[row[0]].right = nodes[row[1]];
            }
        }
        return root;
    }
}


class Solution2 {
    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> map = new HashMap<>();
        Set<Integer> children = new HashSet<>();
        TreeNode root = null;

        for(int[] description : descriptions) {
            int parent = description[0];
            int child = description[1];
            TreeNode parentNode = null, childNode = null;
            if(map.containsKey(parent)) {
                parentNode = map.get(parent);
            } else {
                parentNode = new TreeNode(parent);
                map.put(parent, parentNode);
            }

            if(map.containsKey(child)) {
                childNode = map.get(child);
            } else {
                childNode = new TreeNode(child);
                map.put(child, childNode);
            }

            if(description[2] == 1)
                parentNode.left = childNode;
            else 
                parentNode.right = childNode;
            children.add(child);
        }

        for (Map.Entry<Integer, TreeNode> entry : map.entrySet()) {
            if(!children.contains(entry.getKey()))
                return entry.getValue();
        }
        return null;
    }
}