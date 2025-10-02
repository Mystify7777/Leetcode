//743. Network Delay Time
// https://leetcode.com/problems/network-delay-time/
//copypasted
/*
Step 1: Create a Map of start and end nodes with weight
        1 -> {2,1},{3,2}
        2 -> {4,4},{5,5}
        3 -> {5,3}
        4 ->
        5 ->

Step 2: create a result array where we will keep track the minimum distance to rech end of the node from start node

Step 3: Create a Queue and add starting position with it's weight and add it's reachable distance with increament of own't weight plus a weight require to reach at the end node from start node.
        We keep adding and removing pairs from queue and updating result array as well.

Step 4: find the maximum value from result array:

*/

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        
        //Step 1
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        
        for(int[] time : times) {
            int start = time[0];
            int end = time[1];
            int weight = time[2];
            
            map.putIfAbsent(start, new HashMap<>());
            map.get(start).put(end, weight);
        }
        
         // Step 2
        int[] dis = new int[n+1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[k] = 0;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{k,0});
        
        //Step 3:
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curNode = cur[0];
            int curWeight = cur[1];
            
            for(int next : map.getOrDefault(curNode, new HashMap<>()).keySet()) {
                int nextweight = map.get(curNode).get(next);
                
                if(curWeight + nextweight < dis[next]) {
                    dis[next] = curWeight + nextweight;
                    queue.add(new int[]{next, curWeight + nextweight});
                }
            }
        }
        
        //Step 4:
        int res = 0;
        for(int i=1; i<=n; i++) {
            if(dis[i] > res) {
                res = Math.max(res, dis[i]);
            } 
        }
        
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
/**
class Solution {
    public boolean isChanged(int[][] times,int[] weights){
        int[] copy = Arrays.copyOf(weights, weights.length);
        boolean present=false;
       for(int[] time:times){
        if(copy[time[0]]!=Integer.MAX_VALUE&&weights[time[1]]>time[2]+copy[time[0]]){
            weights[time[1]]=time[2]+copy[time[0]];
            present=true;
        }
       }
       return present;
    }
    public int networkDelayTime(int[][] times, int n, int k) {
        int[] weight=new int[n+1];
        Arrays.fill(weight,Integer.MAX_VALUE);
        weight[k]=0;
        while(true){
            if(!isChanged(times,weight))
                break;
        }
        int max=weight[1];
        for(int i=1;i<=n;i++){
            // System.out.println(weight[i]);
            if(weight[i]==Integer.MAX_VALUE)return -1;
            if(weight[i]>max)max=weight[i];
        }
        return max;
    }
} */