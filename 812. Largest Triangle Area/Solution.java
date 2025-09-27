// 812. Largest Triangle Area
// class Solution {
//     public double largestTriangleArea(int[][] points) {
//         double maxArea = 0.0;
//         int n = points.length;
//         for (int i = 0; i < n; ++i) {
//             for (int j = i + 1; j < n; ++j) {
//                 for (int k = j + 1; k < n; ++k) {
//                     int x1 = points[i][0], y1 = points[i][1];
//                     int x2 = points[j][0], y2 = points[j][1];
//                     int x3 = points[k][0], y3 = points[k][1];
//                     double area = 0.5 * Math.abs(x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2));
//                     maxArea = Math.max(maxArea, area);
//                 }
//             }
//         }
//         return maxArea;
//     }
// }
class Solution {
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double max = 0;
        
        for (int i = 0; i < n; ++i) 
            for (int j = i + 1; j < n; ++j)
                for (int k = j + 1; k < n; ++k) {
                    double area = area(points, i, j, k);
                    if (area > max) {
                        max = area;
                    }
                }
        
        
        return max;
    }
    
    // triangle
    double area(int[][] points, int i, int j, int k) {
        int[] p1 = points[i];
        int[] p2 = points[j];
        int[] p3 = points[k];
        
        double area = 0;
        area += area(p1, p2);
        area += area(p2, p3);
        area += area(p3, p1);
        
        return Math.abs(area);
    }
    
    // right trapezoid
    double area(int[] p1, int[] p2) {
        int w = p2[0] - p1[0];
        double h = (p1[1] + p2[1] + 200) / 2.0;
        return w * h;
    }
}