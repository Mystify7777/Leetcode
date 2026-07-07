// 3093. Longest Common Suffix Queries
// https://leetcode.com/problems/longest-common-suffix-queries/

class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int bestLen = Integer.MAX_VALUE;
        int bestIdx = Integer.MAX_VALUE;
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        TrieNode root = new TrieNode();
        
        for (int i = 0; i < wordsContainer.length; i++) {
            String word = wordsContainer[i];
            int len = word.length();
            TrieNode curr = root;
            
            if (len < curr.bestLen || (len == curr.bestLen && i < curr.bestIdx)) {
                curr.bestLen = len;
                curr.bestIdx = i;
            }
            
            for (int j = len - 1; j >= 0; j--) {
                int charIdx = word.charAt(j) - 'a';
                
                if (curr.children[charIdx] == null) {
                    curr.children[charIdx] = new TrieNode();
                }
                
                curr = curr.children[charIdx];
                
                if (len < curr.bestLen || (len == curr.bestLen && i < curr.bestIdx)) {
                    curr.bestLen = len;
                    curr.bestIdx = i;
                }
            }
        }
        
        int[] ans = new int[wordsQuery.length];
        
        for (int i = 0; i < wordsQuery.length; i++) {
            String query = wordsQuery[i];
            int len = query.length();
            TrieNode curr = root;
            
            for (int j = len - 1; j >= 0; j--) {
                int charIdx = query.charAt(j) - 'a';
                if (curr.children[charIdx] == null) {
                    break;
                }
                curr = curr.children[charIdx];
            }
            ans[i] = curr.bestIdx;
        }
        
        return ans;
    }
} 

class Solution2 {

    /**
     * Time complexity: O(n * nl + m * ml)
     *    n  - length of wordsContainer,
     *    nl - average length of wordsContainer[i],
     *    m  - length of wordsQuery,
     *    ml - average length of wordsQuery[i]
     * Space complexity: O(l)
     *    l  - length of longest word on wordsContainer
     */
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        int maxQueryLength = findMaxQueryLength(wordsQuery);
        Node root = buildTrie(wordsContainer, maxQueryLength);

        int[] result = new int[wordsQuery.length];
        for (int i = 0; i < wordsQuery.length; i++) {
            Node node = root;
            for (int j = wordsQuery[i].length() - 1; j >= 0; j--) {
                int symbolIndex = wordsQuery[i].charAt(j) - 'a';
                Node temp = node.childs[symbolIndex];
                if (temp == null) break;
                node = temp;
            }
            result[i] = node.shortestWordIndex;
        }
        return result;
    }

    private int findMaxQueryLength(String[] wordsQuery) {
        int maxLength = 0;
        for (String query : wordsQuery) {
            if (query.length() > maxLength) {
                maxLength = query.length();
            }
        }
        return maxLength;
    }

    private Node buildTrie(String[] wordsContainer, int maxQueryLength) {
        Node root = new Node();
        for (int i = 0; i < wordsContainer.length; i++) {
            if (wordsContainer[i].length() < wordsContainer[root.shortestWordIndex].length()) {
                root.shortestWordIndex = i;
            }

            Node node = root;
            for (
                int j = wordsContainer[i].length() - 1, k = 0;
                j >= 0 && k < maxQueryLength;
                j--, k++
            ) {
                int symbolIndex = wordsContainer[i].charAt(j) - 'a';
                Node temp = node.childs[symbolIndex];
                if (temp == null) {
                    temp = new Node(i);
                    node.childs[symbolIndex] = temp;
                } else if (wordsContainer[i].length() < wordsContainer[temp.shortestWordIndex].length()) {
                    temp.shortestWordIndex = i;
                }
                node = temp;
            }
        }
        return root;
    }

    private static class Node {
        int shortestWordIndex = 0;
        Node[] childs = new Node[26];

        public Node() {}

        public Node(int shortestWordIndex) {
            this.shortestWordIndex = shortestWordIndex;
        }
    }
}
