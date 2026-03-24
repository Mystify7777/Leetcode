// 2906. Construct Product Matrix
// https://leetcode.com/problems/construct-product-matrix/
// This solution was implemented with assistance from ChatGPT
class Solution2 {

    public int[][] constructProductMatrix(int[][] grid) {
        final int MOD = 12345;
        int n = grid.length;
        int m = grid[0].length;
        int[][] p = new int[n][m];

        long suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                p[i][j] = (int) suffix;
                suffix = (suffix * grid[i][j]) % MOD;
            }
        }

        long prefix = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i][j] = (int) (((long) p[i][j] * prefix) % MOD);
                prefix = (prefix * grid[i][j]) % MOD;
            }
        }

        return p;
    }
}

class Solution {
    public int[][] constructProductMatrix(int[][] g) {
        int m= g.length;
        int n =g[0].length;
        int size =m*n;
        int mod =12345;
        int a[] =new int[size];
        long p[]= new long[size];
        long s[]= new long[size];
        p[0]= g[0][0];
        int idx=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                a[idx++] =g[i][j];
            }
        }
        p[0] =1;
        for(int i=1;i<p.length;i++){
            p[i] =(p[i-1]*a[i-1])%mod;
        }
        s[s.length-1]= 1;
        for(int i=a.length-2;i>=0;i--){
            s[i]= (a[i+1]*s[i+1])%mod;
        }
        idx=0;
        
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                g[i][j]=(int)(p[idx]*s[idx])%mod;
                idx++;
            }
        }
        return g;
        
    }
}