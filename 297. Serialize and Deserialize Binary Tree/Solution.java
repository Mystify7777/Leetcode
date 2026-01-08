/** 297. Serialize and Deserialize Binary Tree
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> q = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.append("n ");
                continue;
            }
            res.append(node.val + " ");
            q.add(node.left);
            q.add(node.right);
        }
        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == "") return null;
        Queue<TreeNode> q = new LinkedList<>();
        String[] values = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        q.add(root);
        for (int i = 1; i < values.length; i++) {
            TreeNode parent = q.poll();
            if (!values[i].equals("n")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                q.add(left);
            }
            if (!values[++i].equals("n")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                q.add(right);
            }
        }
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

/**

public class Codec {

    // Encodes a tree to a single string. bfs
    // public String serialize(TreeNode root) {
    //     if(root == null){
    //         return "";
    //     }
        
    //     Queue<TreeNode> queue = new LinkedList<>();
    //     StringBuilder sb = new StringBuilder();
    //     queue.offer(root);
    //     while(!queue.isEmpty()){
    //         int len = queue.size();
    //         for(int i = 0; i < len; i++) {
    //             TreeNode curr = queue.poll();
    //             if(curr != null){
    //             sb.append(String.valueOf(curr.val) + ",");
    //             queue.offer(curr.left);
    //             queue.offer(curr.right);
    //             }
    //             else{
    //                 sb.append("null,");
    //             }
    //         }
    //     }
    //     sb.deleteCharAt(sb.length()-1);
    //     return sb.toString();
    // }

    // // Decodes your encoded data to tree.
    // public TreeNode deserialize(String data) {
    //     if(data==null || data.length()==0)
    //     return null;
        
    //     String[] arr = data.split(",");
    //     TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
 
 
    // Queue<TreeNode> queue = new LinkedList<>();
    
    // queue.offer(root);
    
    // int i = 1;
    // while(!queue.isEmpty()){
    //     TreeNode curr = queue.poll();
    //     if(curr == null) {
    //         continue;
    //     }
        
    //     if(!arr[i].equals("null")){
    //         curr.left = new TreeNode(Integer.parseInt(arr[i]));
    //         queue.offer(curr.left);
    //     }
    //     else{
    //         curr.left = null;
    //         queue.offer(null);
    //     }
    //     i++;
    //     if(!arr[i].equals("null")){
    //         curr.right = new TreeNode(Integer.parseInt(arr[i]));
    //         queue.offer(curr.right);
    //     }
    //     else{
    //         curr.right = null;
    //         queue.offer(null);
    //     }
    //     i++;
        
    // }

 
    // return root;
    //dfs
     public List<Integer> serialize(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfsSerialize(root, res);
        return res;
    }

    private void dfsSerialize(TreeNode root, List<Integer> res) {
        if (root == null) {
            res.add(Integer.MAX_VALUE);
            return;
        }
        res.add(root.val);
        dfsSerialize(root.left, res);
        dfsSerialize(root.right, res);
    }

    public TreeNode deserialize(List<Integer> data) {
        return dfsDeserialize(data, new int[1]);
    }

    private TreeNode dfsDeserialize(List<Integer> data, int[] index) {
        int cur = data.get(index[0]++);
        if (cur == Integer.MAX_VALUE) return null;
        TreeNode root = new TreeNode(cur);
        root.left = dfsDeserialize(data, index);
        root.right = dfsDeserialize(data, index);
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

//  String SEP = ",";
//     String NULL = "#";

//     /* 主函数，将二叉树序列化为字符串 */
//     public String serialize(TreeNode root) {
//         StringBuilder sb = new StringBuilder();
//         serialize(root, sb);
//         return sb.toString();
//     }

//     /* 辅助函数，将二叉树存入 StringBuilder */
//     void serialize(TreeNode root, StringBuilder sb) {
//         if (root == null) {
//             sb.append(NULL).append(SEP);
//             return;
//         }

//         /******前序遍历位置******/
//         sb.append(root.val).append(SEP);
//         /***********************/

//         serialize(root.left, sb);
//         serialize(root.right, sb);
//     }

//     /* 主函数，将字符串反序列化为二叉树结构 */
//     public TreeNode deserialize(String data) {
//         // 将字符串转化成列表
//         LinkedList<String> nodes = new LinkedList<>();
//         for (String s : data.split(SEP)) {
//             nodes.addLast(s);
//         }
//         return deserialize(nodes);
//     }

//     /* 辅助函数，通过 nodes 列表构造二叉树 
//     TreeNode deserialize(LinkedList<String> nodes) {
//         if (nodes.isEmpty()) return null;

//         /******前序遍历位置******/
//         // 列表最左侧就是根节点
//         String first = nodes.removeFirst();
//         if (first.equals(NULL)) return null;
//         TreeNode root = new TreeNode(Integer.parseInt(first));
//         /***********************/

//         root.left = deserialize(nodes);
//         root.right = deserialize(nodes);

//         return root;
//     }

