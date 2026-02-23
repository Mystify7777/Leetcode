// 1461. Check If a String Contains All Binary Codes of Size K
// https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k
class Solution {
    public boolean hasAllCodes(String s, int k) {
        Set<String> codes = new HashSet<>();
        int total = 1 << k;
        
        for (int i=0; i+k<=s.length(); i++) {
            codes.add(s.substring(i, i+k));
            if (codes.size() == total) return true;
        }
        
        return false;
    }
}
class Solution2 {
    public boolean hasAllCodes(String s, int k) {
        Set<Integer> codes = new HashSet<>();
        int total = 1 << k, allOnes = total - 1, hashCode = 0;
        
        for (int i=0; i<s.length(); i++) {
            hashCode = ((hashCode << 1) & allOnes) | (s.charAt(i) - '0');
            if (i >= k-1 && codes.add(hashCode) && codes.size() == total) return true;
        }
        
        return false;
    }
}

class Solution3 {
    public boolean hasAllCodes(String s, int k) {
        int need = 1 <<k;
        int n = s.length();

        if(n<k || n-k+1 < need)
        return false;

        boolean[] seen = new boolean[need];
        int hash = 0;
        int allOnes = need -1;
        int count = 0;

        for(int i=0; i<n; i++) {
            hash = ((hash << 1) & allOnes) | (s.charAt(i) - '0');
             if(i >= k-1){
                if(!seen[hash]){
                    seen[hash]=true;
                    count++;
                    if(count == need)
                    return true;
                }
             }
        }
        return false;
    }
}