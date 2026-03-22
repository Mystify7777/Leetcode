// https://leetcode.com/problems/determine-whether-matrix-can-be-obtained-by-rotation/
// 1886. Determine Whether Matrix Can Be Obtained By Rotation
class Solution {
    public boolean findRotation(int[][] a, int[][] b) {
       

         int n=a.length;
      int c90=0,c180=0,c270=0,c0=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(b[i][j]==a[n-j-1][i])
                    c90++;
                if(b[i][j]==a[n-i-1][n-j-1])
                    c180++;
                if(b[i][j]==a[j][n-i-1])
                    c270++;
                if(b[i][j]==a[i][j])
                    c0++;
            }
        }
        
        if(c90==n*n||c270==n*n||c180==n*n||c0==n*n)
        return true;
        else return false;
        
    }
}

class Solution2 {
    public void swap(int [][] arr,int i,int j){
        int temp = arr[i][j];
        arr[i][j] = arr[j][i];
        arr[j][i] = temp;
    }

    public void reverse(int [] arr){
        int n = arr.length;
        for(int i = 0;i < n/2;i++){
            int temp = arr[i];
            arr[i] = arr[n-1-i];
            arr[n-1-i] = temp;  
        }
    }

    public void rotate(int [][] mat){
        int n = mat.length;
        for(int i2 = 0;i2 < n-1;i2++){
            for(int j = i2+1;j < n;j++){
                swap(mat,i2,j);
            }
        }
        for(int i1 = 0;i1 < n;i1++){
            reverse(mat[i1]);
        }

    }

    public boolean isEqual(int[][] mat, int[][] target){
        int n = mat.length;
        for(int i = 0;i < n;i++){
        for(int j = 0;j < n;j++){
            if(mat[i][j] != target[i][j]){
                return false;
            }
        }
    }
    return true;
    }

    public boolean findRotation(int[][] mat, int[][] target) {
        int n = mat.length;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                if(isEqual(mat,target)) return true;
                rotate(mat);
            }
        }
    return false;
}
}
