// https://leetcode.com/problems/word-break-ii/
// 140. Word Break II
class Solution {
    private void helper(String s, int i, Set<String> dict, List<String> cur, List<String> res) {
        if (i == s.length()) {
            if (cur.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < cur.size(); j++) {
                    if (j > 0) {
                        sb.append(' ');
                    }
                    sb.append(cur.get(j));
                }
                res.add(sb.toString());
            }
            return;
        }

        for (int j = i+1; j <= s.length(); j++) {
            if (dict.contains(s.substring(i, j))) {
                cur.add(s.substring(i, j));
                helper(s, j, dict, cur, res);
                cur.remove(cur.size() - 1);
            }
        }
    }
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        List<String> res = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        helper(s, 0, dict, cur, res);
        return res;
    }
}
/**
class Solution {
    private TrieNode trieNode = new TrieNode();
    private List<String> result = new ArrayList<>();
    
    public List<String> wordBreak(String s, List<String> wordDict) {
        for (String word : wordDict) insertWord(word);
        dfs(s, 0, this.trieNode, new StringBuilder());
        return this.result;
    }

    private boolean dfs(String s, int idx, TrieNode node, StringBuilder sb) {
        if (null == node) return false;
        if (idx >= s.length()) {
            if (node.isNodeEnd) {
                this.result.add(sb.toString());
                return true;
            };
            return false;
        }
        
        char ch = s.charAt(idx);
        boolean nextIteration = 
            dfs(s, idx + 1, node.children[ch - 'a'], sb.append(ch));
        sb.setLength(sb.length() - 1);

        TrieNode pointer = this.trieNode;
        boolean newWord = false;
        if (pointer.children[ch - 'a'] != null && node.isNodeEnd) {
            sb.append(' ');
            newWord = dfs(s, idx + 1, pointer.children[ch - 'a'], sb.append(ch));
            sb.setLength(sb.length() - 2);
        }

        return newWord || nextIteration;
    }

    private void insertWord(String word) {
        TrieNode pointer = this.trieNode;
        for (char ch : word.toCharArray()) {
            if (null == pointer.children[ch - 'a']) {
                pointer.children[ch - 'a'] = new TrieNode();
            }
            pointer = pointer.children[ch - 'a'];
        }
        pointer.isNodeEnd = true;
    }
}

class TrieNode {
    public boolean isNodeEnd = false;
    public TrieNode[] children = null;

    public TrieNode() {
        this.children = new TrieNode[26];
    }
} */