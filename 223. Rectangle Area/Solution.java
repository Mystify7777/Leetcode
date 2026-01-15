// 223. Rectangle Area
class Solution {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        
        int areaOfSqrA = (C-A) * (D-B);
         int areaOfSqrB = (G-E) * (H-F);
        
        int left = Math.max(A, E);
        int right = Math.min(G, C);
        int bottom = Math.max(F, B);
        int top = Math.min(D, H);
        
        //If overlap
        int overlap = 0;
        if(right > left && top > bottom)
             overlap = (right - left) * (top - bottom);
        
        return areaOfSqrA + areaOfSqrB - overlap;
    }
}

class Solution2 {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int area1 = (ax1 - ax2) * (ay1 - ay2);
        int area2 = (bx1 - bx2) * (by1 - by2);
        
        int overlapWidth = Math.min(ax2, bx2) - Math.max(ax1, bx1);
        int overlapHeight = Math.min(ay2, by2) - Math.max(ay1, by1);

        int overlapArea = 0;
        if(overlapWidth>0 && overlapHeight>0)
            overlapArea = overlapHeight * overlapWidth;


        return area1+area2 - overlapArea;
    }
}