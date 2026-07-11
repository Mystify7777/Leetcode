// 3838. Weighted Word Mapping
// https://leetcode.com/problems/weighted-word-mapping/
class Solution2 {
    public String mapWordWeights(String[] words, int[] wt) {
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            int s = 0;
            for (int i = 0; i < word.length(); i++) 
                s += wt[(word.charAt(i) & (1 << 5) - 1) - 1];
            
            sb.append((char) ('z' - (s - ((s * 2521) >> (1 << 4)) * 26)));
        }

        return sb.toString();
    }
}


class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        int [] arr = new int[words.length];
        int sum =0;
        int j =0;
        for(String word :words)
        {
            for(int i =0;i<word.length();i++)
            {
                sum=sum+weights[(int)word.charAt(i)-'a'];
            }
            arr[j]=sum%26;
            sum=0;
            j++;
        }
        StringBuilder temp = new StringBuilder();
        for(int i =0;i<arr.length;i++)
        {
            temp.append((char)('z'-arr[i]));
        }
        return temp.toString();

    }
        
    }