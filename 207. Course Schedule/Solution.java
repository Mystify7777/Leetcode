// 207. Course Schedule
class Solution {
    public boolean canFinish(int n, int[][] prerequisites) {
        List<Integer>[] adj = new List[n];
        int[] indegree = new int[n];
        List<Integer> ans = new ArrayList<>();

        for (int[] pair : prerequisites) {
            int course = pair[0];
            int prerequisite = pair[1];
            if (adj[prerequisite] == null) {
                adj[prerequisite] = new ArrayList<>();
            }
            adj[prerequisite].add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            ans.add(current);

            if (adj[current] != null) {
                for (int next : adj[current]) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }

        return ans.size() == n;
    }
}
/**
class Solution {
     static{
        for(int i = 0; i < 500; i++){
            canFinish(0, new int[][]{});
        }
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] adj = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) adj[i] = new ArrayList<>();
        for (int[] p : prerequisites) {
            int course = p[0], pre = p[1];
            adj[pre].add(course);
        }

        boolean[] visited = new boolean[numCourses];
        boolean[] onPath = new boolean[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (!visited[i]) {
                if (hasCycle(i, adj, visited, onPath)) return false;
            }
        }
        return true;
    }

    private static boolean hasCycle(int node, List<Integer>[] adj, boolean[] visited, boolean[] onPath) {
        visited[node] = true;
        onPath[node] = true;

        for (int nei : adj[node]) {
            if (!visited[nei]) {
                if (hasCycle(nei, adj, visited, onPath)) return true;
            } else if (onPath[nei]) {
                return true;
            }
        }

        onPath[node] = false;
        return false;
    }
} */