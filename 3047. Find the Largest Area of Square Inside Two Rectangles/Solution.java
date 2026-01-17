// 3047. Find the Largest Area of Square Inside Two Rectangles
// https://leetcode.com/problems/find-the-largest-area-of-square-inside-two-rectangles/
class Solution {
    public long largestSquareArea(int[][] bl, int[][] tr) {
        int s = 0;
        int n = bl.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int minX = Math.max(bl[i][0], bl[j][0]);
                int maxX = Math.min(tr[i][0], tr[j][0]);
                int minY = Math.max(bl[i][1], bl[j][1]);
                int maxY = Math.min(tr[i][1], tr[j][1]);

                if (minX < maxX && minY < maxY) {
                    int len = Math.min(maxX - minX, maxY - minY);
                    s = Math.max(s, len);
                }
            }
        }

        return (long) s * s;
    }
}

class Solution2 {

    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        long maxSide = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int w =
                    Math.min(topRight[i][0], topRight[j][0]) -
                    Math.max(bottomLeft[i][0], bottomLeft[j][0]);
                int h =
                    Math.min(topRight[i][1], topRight[j][1]) -
                    Math.max(bottomLeft[i][1], bottomLeft[j][1]);
                int side = Math.min(w, h);

                maxSide = Math.max(maxSide, side);
            }
        }

        return maxSide * maxSide;
    }
}