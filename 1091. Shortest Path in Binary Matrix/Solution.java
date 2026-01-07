// 1091. Shortest Path in Binary Matrix
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {

    //check if grid is empty
    if(grid==null || grid.length==0 || grid[0].length==0 ){
        return -1;
    }
    int rowLen = grid.length;
    int columnLen = grid[0].length;

    //check if start and end exist
    if(grid[0][0]==1 || grid[rowLen-1][columnLen-1]==1){
       return -1; 
    }

    //all possible adjacent directions from a cell
    //  ↖  ^  ↗
    //  <[r,c]>
    //  ↙  ↓  ↘
    int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    
    //to keep track of visited cells
    boolean[][] visited = new boolean[rowLen][columnLen];

    //Queue to keep track of different levels of cells
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0}); //adding start cell
    visited[0][0] = true;
    int pathLen = 0;//Keep track of path length

    //with all the checks done we are now sure there is a starting and an end
    //Now we check all adjacent cells until we reach the end
    while(!queue.isEmpty()){
        int size = queue.size();
        pathLen++;

        for(int i=0; i<size; i++){
            int[] currentCell = queue.poll();

            //check if we have reached the end 
            if(currentCell[0] ==rowLen-1 && currentCell[1] == columnLen-1){
                return pathLen;
            }

            //check all adjacent positions from the current cell(level)
            for(int[] dir:directions){
                int nextX = currentCell[0]+dir[0];
                int nextY = currentCell[1]+dir[1];

                //check if the adjacent is not valid/already visited/1
                //  then ignore the cell if it is
                if(nextX < 0 || nextX >= rowLen
                    || nextY<0 || nextY >= columnLen 
                    || visited[nextX][nextY] || grid[nextX][nextY] == 1){
                    continue;
                }

                //add the valid cell to the queue
                visited[nextX][nextY] = true;
                queue.offer(new int[]{nextX,nextY});
            }
        }
    }
    return -1;
}

}

//clear this alternate implementation

class Solution2 {
    private static int bfs(final int[][] grid) {
        final var fromStart = new BFSRunner(grid, 0, 0, 's', 'e');
        final var fromEnd = new BFSRunner(grid, grid.length - 1, grid.length - 1, 'e', 's');
        int steps = 1;

        while (!(fromStart.queue.isEmpty() || fromEnd.queue.isEmpty())) {
            ++steps;

            if (fromStart.step()) {
                return steps;
            }

            ++steps;

            if (fromEnd.step()) {
                return steps;
            }
        }

        return -1;
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] != 0 || grid[grid.length - 1][grid.length - 1] != 0) {
            return -1;
        }

        if (grid.length == 1) {
            return 1;
        }

        return bfs(grid);
    }

    private record Point(int y, int x) {
    }

    static class BFSRunner {
        final int end;
        final int[][] grid;
        final int mark;
        final int other;
        ArrayDeque<Point> queue;

        BFSRunner(int[][] grid, int y, int x, int mark, int other) {
            this.grid = grid;
            this.end = grid.length - 1;
            this.mark = mark;
            this.other = other;
            queue = new ArrayDeque<>();
            queue.add(new Point(y, x));
            grid[y][x] = mark;
        }

        boolean step() {
            for (int c = queue.size(); c > 0; --c) {
                final var p = queue.poll();
                final int yMin = Math.max(0, p.y() - 1);
                final int yMax = Math.min(end, p.y() + 1);
                final int xMin = Math.max(0, p.x() - 1);
                final int xMax = Math.min(end, p.x() + 1);

                for (int y = yMin; y <= yMax; ++y) {
                    for (int x = xMin; x <= xMax; ++x) {
                        if (grid[y][x] != 0) {
                            if (grid[y][x] == other) {
                                return true;
                            }

                            continue;
                        }

                        queue.add(new Point(y, x));
                        grid[y][x] = mark;
                    }
                }
            }

            return false;
        }
    }
}