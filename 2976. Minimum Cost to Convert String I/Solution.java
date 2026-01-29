// 2976. Minimum Cost to Convert String I
//cp
class Solution {
    private static final int CHAR_COUNT = 26;
    private static final int INF = Integer.MAX_VALUE / 2;

    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int[][] conversionGraph = buildConversionGraph(original, changed, cost);
        optimizeConversionPaths(conversionGraph);
        return computeTotalConversionCost(source, target, conversionGraph);
    }

    private int[][] buildConversionGraph(char[] original, char[] changed, int[] cost) {
        int[][] graph = new int[CHAR_COUNT][CHAR_COUNT];
        for (int[] row : graph) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < CHAR_COUNT; i++) {
            graph[i][i] = 0;
        }
        for (int i = 0; i < cost.length; i++) {
            int from = original[i] - 'a';
            int to = changed[i] - 'a';
            graph[from][to] = Math.min(graph[from][to], cost[i]);
        }
        return graph;
    }

    private void optimizeConversionPaths(int[][] graph) {
        for (int k = 0; k < CHAR_COUNT; k++) {
            for (int i = 0; i < CHAR_COUNT; i++) {
                if (graph[i][k] < INF) {
                    for (int j = 0; j < CHAR_COUNT; j++) {
                        if (graph[k][j] < INF) {
                            graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                        }
                    }
                }
            }
        }
    }

    private long computeTotalConversionCost(String source, String target, int[][] graph) {
        long totalCost = 0;
        for (int i = 0; i < source.length(); i++) {
            int sourceChar = source.charAt(i) - 'a';
            int targetChar = target.charAt(i) - 'a';
            if (sourceChar != targetChar) {
                if (graph[sourceChar][targetChar] == INF) {
                    return -1L;
                }
                totalCost += graph[sourceChar][targetChar];
            }
        }
        return totalCost;
    }
}

class Solution2 {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int[][] dis = new int[26][26];
        for(int i = 0 ; i < 26 ;i++){
            Arrays.fill(dis[i] , Integer.MAX_VALUE);
            dis[i][i] = 0 ;
        }
        
        for(int i = 0 ; i < cost.length ; i++){
            dis[original[i] - 'a'][changed[i] - 'a'] = Math.min(dis[original[i] - 'a'][changed[i] - 'a'],cost[i]);
        }

        for(int k = 0 ; k < 26 ; k++){
            for(int i = 0 ; i < 26 ; i++){
                if(dis[i][k] < Integer.MAX_VALUE){
                    for(int j = 0 ; j < 26 ; j++){
                        if(dis[k][j] < Integer.MAX_VALUE){
                            dis[i][j] = Math.min(dis[i][j] , dis[i][k] + dis[k][j]);
                        }
                    }
                }
            }
        }

        long ans = 0L ;
        for(int i = 0 ; i <source.length() ;i++){
            int c1 = source.charAt(i) - 'a';
            int c2 = target.charAt(i) - 'a';
            if(dis[c1][c2] == Integer.MAX_VALUE){
                return -1L;
            }
            ans += (long)dis[c1][c2];
        }

        return ans ;
    }
}