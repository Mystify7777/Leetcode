// 130. Surrounded Regions
// https://leetcode.com/problems/surrounded-regions/
class Solution {
   public void solve(char[][] board) {
	// if (board.length == 0 || board[0].length == 0)
	// 	return;
	if (board.length < 2 || board[0].length < 2)
		return;
	int m = board.length, n = board[0].length;
	//Any 'O' connected to a boundary can't be turned to 'X', so ...
	//Start from first and last column, turn 'O' to '*'.
	for (int i = 0; i < m; i++) {
		if (board[i][0] == 'O')
			boundaryDFS(board, i, 0);
		if (board[i][n-1] == 'O')
			boundaryDFS(board, i, n-1);	
	}
	//Start from first and last row, turn '0' to '*'
	for (int j = 0; j < n; j++) {
		if (board[0][j] == 'O')
			boundaryDFS(board, 0, j);
		if (board[m-1][j] == 'O')
			boundaryDFS(board, m-1, j);	
	}
	//post-prcessing, turn 'O' to 'X', '*' back to 'O', keep 'X' intact.
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			if (board[i][j] == 'O')
				board[i][j] = 'X';
			else if (board[i][j] == '*')
				board[i][j] = 'O';
		}
	}
}
//Use DFS algo to turn internal however boundary-connected 'O' to '*';
private void boundaryDFS(char[][] board, int i, int j) {
	if (i < 0 || i > board.length - 1 || j <0 || j > board[0].length - 1)
		return;
	if (board[i][j] == 'O')
		board[i][j] = '*';
	if (i > 1 && board[i-1][j] == 'O')
		boundaryDFS(board, i-1, j);
	if (i < board.length - 2 && board[i+1][j] == 'O')
		boundaryDFS(board, i+1, j);
	if (j > 1 && board[i][j-1] == 'O')
		boundaryDFS(board, i, j-1);
	if (j < board[i].length - 2 && board[i][j+1] == 'O' )
		boundaryDFS(board, i, j+1);
}
}
/**
class Solution {
    public void solve(char[][] grid) {
        for(int i=0;i<grid.length;i++){
            if(grid[i][0]!='X')
            DFS(i,0,grid);
            if(grid[i][grid[0].length-1]!='X')
            DFS(i,grid[0].length-1,grid);
        }
        for(int i=0;i<grid[0].length-1;i++){
            if(grid[0][i]!='X')
            DFS(0,i,grid);
            if(grid[grid.length-1][i]!='X')
            DFS(grid.length-1,i,grid);
        }
        swap(grid,'O','X');
        swap(grid,'p','O');  
    }

    void swap(char[][] grid,char p, char q){
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++)
                    if(grid[i][j]==p) grid[i][j]=q;
    }

    void DFS(int i,int j, char[][] grid){
        if(i<0|| j<0 || i>=grid.length || j>=grid[0].length || grid[i][j]!='O') return ;
        grid[i][j]='p';
        DFS(i+1,j,grid);
        DFS(i,j+1,grid);
        DFS(i-1,j,grid);
        DFS(i,j-1,grid);
    }
} */