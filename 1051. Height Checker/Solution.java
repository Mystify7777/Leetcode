// 1051. Height Checker
class Solution {
    public int heightChecker(int[] heights) {
        int[] expected = heights.clone();
        Arrays.sort(expected);
        int count = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] != expected[i])
                count++;
        }
        return count;
    }
}
class Solution2 {
    public int heightChecker(int[] heights) {
        int[] expected=new int[101];
        for(int h:heights){
            expected[h]++;
        }
        int ind=0,mis=0;
        for(int h=1;h<=100;h++){
            while(expected[h]>0){
                if(heights[ind]!=h){
                    mis++;
                }
                ind++;
                expected[h]--;
            }
        }
        return mis;
    }
}