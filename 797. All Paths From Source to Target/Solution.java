//copypasted
// 797. All Paths From Source to Target
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> ans = new LinkedList();
        List<Integer> current = new ArrayList();
        current.add(0);
        dfs(0,current,graph,graph.length-1,ans);
        return ans; 
    }
    private void dfs(int src, List<Integer> current, int graph[][], int dest, List<List<Integer>> ans){
        if(src == dest){
            ans.add(new ArrayList(current));
            return;
        }
        for(int n : graph[src]){
            current.add(n);
            dfs(n,current,graph,dest,ans);
            current.remove(current.size()-1);
        }
    }
}
/**
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
         List<List<Integer>>res=new ArrayList<>();
         List<Integer>path=new ArrayList<>();
         path.add(0);
         dfs(0,graph,path,res);
         return res;
      }
    private void  dfs(int node,int graph[][],List<Integer>path, List<List<Integer>>res){
        if(node==graph.length-1){
            res.add(new ArrayList<>(path));
            return;
        }

        for(int nei:graph[node]){
            path.add(nei);
            dfs(nei,graph,path,res);
            path.remove(path.size()-1);
        }
      }
        
} */