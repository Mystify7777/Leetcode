// 904. Fruit Into Baskets
class Solution2 {
    public int totalFruit(int[] fruits) {
        int start = 0,end = 0;
        int n = fruits.length,maxLen = 0;
        Map<Integer,Integer> map = new HashMap<>();
        while(end<n)
        {
            map.put(fruits[end],map.getOrDefault(fruits[end],0)+1);
            while(map.size()>=3)
            {
                map.put(fruits[start],map.get(fruits[start])-1);
                if(map.get(fruits[start]) == 0) map.remove(fruits[start]);
                start++;
            }
            int currLen = end-start+1;
            maxLen = Math.max(maxLen,currLen);
            end++;
        }
        return maxLen;
    }
}

class Solution {
    public int totalFruit(int[] fruits) {
        int i = 0, j = 0, n = fruits.length;
        while (j < n && fruits[i] == fruits[j]) {
            j++;
        }
        if (j == n) return n;

        int l = i, r = j + 1, ans = 0;
        while (r < n) {
            if (fruits[r] != fruits[j]) {
                if (fruits[r] == fruits[i]) {
                    i = j;
                    j = r;
                } else {
                    ans = Math.max(ans, r - l);
                    i = j;
                    l = j;
                    j = r;
                }
            }
            r++;
        }
        ans = Math.max(ans, r - l);
        return ans;
    }
}