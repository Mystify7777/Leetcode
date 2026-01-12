// 1266. Minimum Time Visiting All Points
class Solution {
    public int minTimeToVisitAllPoints(int[][] points) {
        int res = 0;

        for (int i = 1; i < points.length; i++) {
            res += Math.max(
                Math.abs(points[i][0] - points[i - 1][0]),
                Math.abs(points[i][1] - points[i - 1][1])
            );
        }

        return res;        
    }
}
// why is this modular approach faster ?
class Solution2 {
    public int minTimeToVisitAllPoints(int[][] points) {
        int ans = 0;
        for(int i=0;i<points.length-1;i++){
            ans+=minTime(points[i],points[i+1]);
        }
        return ans;
    }
    
    private int minTime(int[] a,int[] b){
        int dx = Math.abs(a[0]-b[0]);
        int dy = Math.abs(a[1]-b[1]);
        return Math.max(dx,dy);
    }
}