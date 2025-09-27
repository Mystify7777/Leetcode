// 210. Course Schedule II
public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //prepare
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i < numCourses; i++){
            graph.add(new ArrayList<>());
        }

        for(int[] pair : prerequisites){
            int prev = pair[1];
            int next = pair[0];
            graph.get(prev).add(next);
        }

        Map<Integer, Integer> visited = new HashMap<>();
        //initail visited
        for(int i = 0; i < numCourses; i++){
            visited.put(i, 0);//0 -> unvisited, 1 -> visiting, 2 -> visited
        }

        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < numCourses; i++){
            if(!topoSort(res, graph, visited, i)) return new int[0];
        }

        int[] result = new int[numCourses];
        for(int i = 0; i < numCourses; i++){
            result[i] = res.get(numCourses - i - 1);
        }
        return result;
    }

    //the return value of this function only contains the ifCycle info and does not interfere dfs process. if there is Cycle, then return false
    private boolean topoSort(List<Integer> res, List<List<Integer>> graph, Map<Integer, Integer> visited, int i){
        int visit = visited.get(i);
        if(visit == 2){//when visit = 2, which means the subtree whose root is i has been dfs traversed and all the nodes in subtree has been put in the result(if we request), so we do not need to traverse it again
            return true;
        }if(visit == 1){
            return false;
        }

        visited.put(i, 1);
        for(int j : graph.get(i)){
            if(!topoSort(res, graph, visited, j)) return false;
        }
        visited.put(i, 2);
        res.add(i);//the only difference with traversing a graph

        return true;
    }
}

/**

class Solution {
     private int top;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        top = numCourses - 1;

        int[] postList = new int[numCourses];
        int[] nextIndex = new int[prerequisites.length];
        int[] nextNode = new int[prerequisites.length];

        for (int i = 0; i < numCourses; i++) {
            postList[i] = -1;
        }

        int index = 0;
        for (int[] prerequisite : prerequisites) {
            nextIndex[index] = postList[prerequisite[1]];
            nextNode[index] = prerequisite[0];
            postList[prerequisite[1]] = index;
            index++;
        }

        int[] stack = new int[numCourses];
        int[] visited = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (0 == visited[i]) {
                if (!DFS(i, visited, postList, nextIndex, nextNode, stack)) {
                    return new int[0];
                }
            }
        }
        return stack;
    }

    private boolean DFS(int numCourses, int[] visited, int[] postList, int[] nextIndex, int[] nextNode, int[] stack) {
        visited[numCourses] = 1;
        for (int i = postList[numCourses]; i != -1; i = nextIndex[i]) {
            if (1 == visited[nextNode[i]]) {
                return false;
            }
            if (0 == visited[nextNode[i]]) {
                if (!DFS(nextNode[i], visited, postList, nextIndex, nextNode, stack)) {
                    return false;
                }
            }
        }
        visited[numCourses] = 2;
        stack[top] = numCourses;
        top--;
        return true;
    }
} */