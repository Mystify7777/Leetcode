// 934. Shortest Bridge
class Solution 
{
    public int shortestBridge(int[][] grid) 
    {
        Queue<int[]> q= new LinkedList<>();
        boolean flag= false;
        for(int i= 0; i< grid.length; i++)
        {
            for(int j= 0; j< grid[0].length; j++)
            {
                if(grid[i][j] == 1)//finding one island cell, to traverse the whole component and add it to the Queue 
                {
                    dfs(grid, i, j, q);//Connected island cells 
                    flag= true;
                    break;
                }
            }
            if(flag == true)break;
        }
        
        return findNearestIslandBFS(grid, q);
    }
    
    private void dfs(int[][] grid, int i, int j, Queue<int[]> q)
    {//Preorder DFS is used to add the sub-land of island into the queue
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == -1 || grid[i][j] == 0)
            return;//base case 1)index out of  bound 2)not visiting the visited node  3)not visiting the water cell 
        
        grid[i][j]= -1;//marking the node as visited, so that we don't traverse the node again 
        q.offer(new int[]{i, j});//adding the sub-land of the island into the Queue 
        
        //CSS Border Order
        dfs(grid, i-1, j, q);//Top
        dfs(grid, i, j+1, q);//Right
        dfs(grid, i+1, j, q);//Bottom 
        dfs(grid, i, j-1, q);//Left
        
        return;
    }
    
    private int findNearestIslandBFS(int[][] grid, Queue<int[]> q)
    {//BFS finds the shortest path length between the two points, so we use here to find the minimum distance between two point of island //Top to Bottom Approach //Multiple Variate BFS 
        int[][] trav= {{-1,0},{0,1},{1,0},{0,-1}};//Traversing in 4 Direction 
        
        int level= 0;//current level//source starting island level 
        
        while(!q.isEmpty())
        {
            int size= q.size();//Level popping constraint 
            while(size-- > 0)//Radially traversing breadth wise 
            {
                int []temp= q.poll();//polling the current node 
                for(int []dirc: trav)
                {
                    //new co-ordinate 
                    int i_= temp[0]+ dirc[0];
                    int j_= temp[1]+ dirc[1];
                    if(i_ >= grid.length || i_ < 0 || j_ >= grid[0].length || j_ < 0 || grid[i_][j_] == -1)
                        continue;//base case 1)index out of bound 2)not visiting the visited node again 
                    
                    if(grid[i_][j_] == 1)//when we find a island cell, we return the current level(n-1)
                        return level;//previous level of the destination level//0 1 2 ... (n-1) n, [1 -> n-1] swap required//returning the n-1 level //n -> level of the destination level, 0 -> source starting island level
                    
                    else
                    {//water cell case, we are adding it to the next level of the queue, to expand the search and to find the min path length between two island
                        grid[i_][j_]= -1;//marking it as visited, so that we dont traverse it again 
                        q.offer(new int[]{i_, j_});//adding the the water cell into the Queue 
                    }
                }
            }
            level+= 1;//increasing the level 
        }
        return -1;//base case
    }
}//Please do Upvote, it helps a lot 

/**
class Pair{
    int row;
    int col;
    public Pair(int row, int col){
        this.row=row;
        this.col=col;
    }
}
class Solution {
    public int shortestBridge(int[][] grid) {
        Queue<Pair> queue=new LinkedList<>();
        boolean flag=false;
        int n=grid.length;
        boolean[][] visited=new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    dfs(i,j,n,visited,grid, queue);
                    flag=true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        int[] dRow={-1,0,1,0};
        int[] dCol={0,1,0,-1};
        int change=0;
        while(!queue.isEmpty()){
            int size=queue.size();
            while(size>0){
                Pair p=queue.poll();
                int row=p.row;
                int col=p.col;
                for(int i=0;i<4;i++){
                    int R=row+dRow[i];
                    int C=col+dCol[i];
                    if(R>=0 && R<n && C>=0 && C<n && !visited[R][C]){
                        if(grid[R][C]==1){
                            return change;
                        }
                        visited[R][C]=true;
                        queue.add(new Pair(R,C));
                    }
                }
                size--;
            }
            change++;
        }
        return -1;
    }
    public void dfs(int row, int col, int n, boolean[][] visited, int[][] grid, Queue<Pair> queue){
        if(row<0 || row>=n || col<0 || col>=n || visited[row][col] || grid[row][col]!=1){
            return;
        }
        visited[row][col]=true;
        queue.add(new Pair(row, col));
        dfs(row+1,col,n,visited,grid,queue);
        dfs(row-1,col,n,visited,grid,queue);
        dfs(row,col+1,n,visited,grid,queue);
        dfs(row,col-1,n,visited,grid,queue);
    }
} */