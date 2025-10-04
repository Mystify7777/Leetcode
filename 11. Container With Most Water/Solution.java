// 11. Container With Most Water
// https://leetcode.com/problems/container-with-most-water/
class Solution {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;        
    }
}

/**

class Solution {
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length -1;
        int area = 0;
        int maxArea = 0;
        
        while (i < j){
            int min = Math.min(height[i], height[j]);
            area = min * (j - i);
            maxArea = Math.max(area, maxArea);
            while(i < j && height[i] <= min) i++;
            while(i < j && height[j] <= min) j--;
        }
        return maxArea;
    }
} */