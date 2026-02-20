// 761. Special Binary String
// https://leetcode.com/problems/special-binary-string/
class Solution {
    public String makeLargestSpecial(String s) {
        int count = 0, i = 0;
        List<String> res = new ArrayList<>();
        
        for (int j = 0; j < s.length(); j++) {
            // Track balance: +1 for '1', -1 for '0'
            if (s.charAt(j) == '1') count++;
            else count--;
            
            // Found a balanced chunk when count returns to 0
            if (count == 0) {
                // Recursively maximize inner part, wrap with 1...0
                res.add('1' + makeLargestSpecial(s.substring(i + 1, j)) + '0');
                i = j + 1; // Move to next potential chunk
            }
        }
        
        // Sort chunks in descending order for largest arrangement
        Collections.sort(res, Collections.reverseOrder());
        return String.join("", res);
    }
}

class AlternateSolution {
    public String makeLargestSpecial(String S) {
        if(S == null || S.length() == 0 || S.length() == 2) return S;
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> b.compareTo(a));
        int acc = 1, prev = 0;
        for(int i = 1; i <= S.length(); i++) {
            if(acc == 0) {
                if(!(prev == 0 && i == S.length())) pq.add(makeLargestSpecial(S.substring(prev, i)));
                prev = i;
            }
            if(i == S.length()) break;
            if(S.charAt(i) == '1') {
                acc++;
            }
            else {
                acc--;
            }
        }
        StringBuilder ans = new StringBuilder();
        while(!pq.isEmpty()) {
            ans.append(pq.poll());
        }
        if(ans.length() == 0) {
            ans.append('1');
            ans.append(makeLargestSpecial(S.substring(1, S.length() - 1)));
            ans.append('0');
        }
        return ans.toString();
    }
}