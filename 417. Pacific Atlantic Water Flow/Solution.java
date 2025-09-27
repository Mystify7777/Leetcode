
// 417. Pacific Atlantic Water Flow
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];
        
        for (int col = 0; col< cols; col++){
            dfs(0, col, pacific, heights[0][col], heights);
            dfs(rows-1, col, atlantic, heights[rows-1][col], heights);
        }
        
        for (int row = 0; row<rows; row++){
            dfs(row, 0, pacific, heights[row][0], heights);
            dfs(row, cols-1, atlantic, heights[row][cols-1], heights);
        }
        
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                if (pacific[i][j] && atlantic[i][j])
                    result.add(Arrays.asList(i,j));
            }
        }
        return result;
    }
    
    private void dfs(int row, int col, boolean[][] visited, int prevHeight, int[][] heights){
        if (row < 0 || row >= heights.length || col < 0 || col >= heights[0].length || visited[row][col] || prevHeight > heights[row][col])
            return;
        visited[row][col]= true;
        dfs(row+1, col, visited, heights[row][col], heights);
        dfs(row-1, col, visited, heights[row][col], heights);
        dfs(row, col+1, visited, heights[row][col], heights);
        dfs(row, col-1, visited, heights[row][col], heights);
    }
    
}
