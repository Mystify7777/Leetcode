// 1345. Jump Game IV
// https://leetcode.com/problems/jump-game-iv/
import java.util.*;

class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;

        Map<Integer, List<Integer>> mp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            mp.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});

        boolean[] vis = new boolean[n];
        vis[0] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int node = curr[0];
            int dist = curr[1];

            if (node == n - 1) return dist;

            if (node - 1 >= 0 && !vis[node - 1]) {
                vis[node - 1] = true;
                q.offer(new int[]{node - 1, dist + 1});
            }

            if (node + 1 < n && !vis[node + 1]) {
                vis[node + 1] = true;
                q.offer(new int[]{node + 1, dist + 1});
            }

            for (int next : mp.get(arr[node])) {
                if (!vis[next]) {
                    vis[next] = true;
                    q.offer(new int[]{next, dist + 1});
                }
            }

            mp.get(arr[node]).clear();
        }

        return -1;
    }
}

class Solution2 {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if(n==1) return 0;

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for(int i=0; i<n; i++) {
            if(!map.containsKey(arr[i])) map.put(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n];
        q.add(n-1);
        vis[n-1]=true;
        int count=0, x;

        while(!q.isEmpty()) {
            int sz = q.size();
            count++;
            for(int k=0; k<sz; k++) {
                int ind = q.poll();
                
                x=ind-1;
                if(x==0) return count;
                if(x>=0 && x<n && !vis[x]) {vis[x]=true; q.add(x);}

                x=ind+1;
                if(x==0) return count;
                if(x>=0 && x<n && !vis[x]) {vis[x]=true; q.add(x);}

                List<Integer> list = map.get(arr[ind]);
                if(list!=null) {
                for(int i : list){
                    if(!vis[i]) {
                        if(i==0) return count;
                        vis[i]=true;
                        q.add(i);
                    }
                }
                }
                map.remove(arr[ind]);
            }
        }
        return -1;
    }
}
