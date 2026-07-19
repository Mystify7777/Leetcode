// 1081. Smallest Subsequence of Distinct Characters
// https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/

class Solution1 {
    public String smallestSubsequence(String s) {
        int[] freq = new int[27];
        boolean[] seen = new boolean[27];
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++)
            freq[s.charAt(i) & 31]++;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int x = c & 31;
            freq[x]--;

            if (seen[x])
                continue;

            while (!stack.isEmpty()) {
                if (stack.peek() <= c)
                    break;

                if (freq[stack.peek() & 31] == 0)
                    break;

                seen[stack.peek() & 31] = false;
                stack.pop();
            }

            stack.push(c);
            seen[x] = true;
        }

        StringBuilder res = new StringBuilder();

        for (char c : stack)
            res.append(c);

        return res.toString();
    }
}

class Solution { public String smallestSubsequence(String text) { StringBuilder sb = new StringBuilder(); int[] count = new int[128]; boolean[] used = new boolean[128]; for (final char c : text.toCharArray()) ++count[c]; for (final char c : text.toCharArray()) { --count[c]; if (used[c]) continue; while (sb.length() > 0 && last(sb) > c && count[last(sb)] > 0) { used[last(sb)] = false; sb.setLength(sb.length() - 1); } used[c] = true; sb.append(c); } return sb.toString(); } private char last(StringBuilder sb) { return sb.charAt(sb.length() - 1); } }