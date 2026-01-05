// 14. Longest Common Prefix
class Solution {
    public String longestCommonPrefix(String[] v) {
        StringBuilder ans = new StringBuilder();
        Arrays.sort(v);
        String first = v[0];
        String last = v[v.length-1];
        for (int i=0; i<Math.min(first.length(), last.length()); i++) {
            if (first.charAt(i) != last.charAt(i)) {
                return ans.toString();
            }
            ans.append(first.charAt(i));
        }
        return ans.toString();
    }
}

// umm why is this alternate approach fast??

/**
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int minSize = Integer.MAX_VALUE;
        String smallestString = "";
        for (String str : strs)
            minSize = Math.min(minSize, str.length());
        //System.out.println(minSize);
        int low = 1;
        int high = minSize;
        
        while(low <= high) {
            int middle = (low + high)/2;
            if(isCommonPrefix(strs, middle)) {
                low = middle + 1;
            } else {
                high = middle -1;
            }
        }
        return strs[0].substring(0, (low + high) / 2);
    }
    
    private boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0,len);
        for (int i = 1; i < strs.length; i++)
            if (!strs[i].startsWith(str1))
                return false;
        return true;
    }
}
 */