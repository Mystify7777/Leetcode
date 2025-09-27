// 84. Largest Rectangle in Histogram
//copypasted
class Solution {
   public static int largestRectangleArea(int[] height) {
    if (height == null || height.length == 0) {
        return 0;
    }
    int[] lessFromLeft = new int[height.length]; // idx of the first bar the left that is lower than current
    int[] lessFromRight = new int[height.length]; // idx of the first bar the right that is lower than current
    lessFromRight[height.length - 1] = height.length;
    lessFromLeft[0] = -1;

    for (int i = 1; i < height.length; i++) {
        int p = i - 1;

        while (p >= 0 && height[p] >= height[i]) {
            p = lessFromLeft[p];
        }
        lessFromLeft[i] = p;
    }

    for (int i = height.length - 2; i >= 0; i--) {
        int p = i + 1;

        while (p < height.length && height[p] >= height[i]) {
            p = lessFromRight[p];
        }
        lessFromRight[i] = p;
    }

    int maxArea = 0;
    for (int i = 0; i < height.length; i++) {
        maxArea = Math.max(maxArea, height[i] * (lessFromRight[i] - lessFromLeft[i] - 1));
    }

    return maxArea;
}
}
/*
class Solution {
    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        int[] stack = new int[length];
        int top = -1;
        int max = 0;
        for (int i = 0; i < length; i++) {
            while (top > -1 && heights[i] <= heights[stack[top]]) {
                int height = heights[stack[top--]];
                int left = (top > -1) ? stack[top] : -1;
                int width = i - left - 1;
                max = Math.max(max, width * height);
            }
            if (top >= 0 && heights[i] == heights[stack[top]]) stack[top] = i;
            else stack[++top] = i;
        }
        while (top > -1) {
            int height = heights[stack[top--]];
            int left = (top > -1) ? stack[top] : -1;
            int width = length - left - 1;
            max = Math.max(max, width * height);
        }
        return max;
    }
}*/