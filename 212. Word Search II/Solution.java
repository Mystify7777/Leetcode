//copypaste
// 212. Word Search II
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
    Trie trie = buildTrie(words);
    Set<String> res = new HashSet<>();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, trie, res, i, j);
        }
    }
    return new ArrayList<>(res);
}

public void dfs(char[][] board, Trie node, Set<String> res, int i, int j) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
        board[i][j] == '#' || node.next[board[i][j] - 'a'] == null) {
            return;
    }
    if (node.next[board[i][j] - 'a'].word != null) {
        res.add(node.next[board[i][j] - 'a'].word);
    }

    // Go to next char
    node = node.next[board[i][j] - 'a']; 
    char c = board[i][j];
    board[i][j] = '#';
    dfs(board, node, res, i - 1, j);
    dfs(board, node, res, i + 1, j);
    dfs(board, node, res, i, j - 1);
    dfs(board, node, res, i, j + 1);
    board[i][j] = c;
}   

public Trie buildTrie(String[] words) {
    Trie root = new Trie();
    for (String w : words) {
        Trie p = root;
        for (char c : w.toCharArray()) {
            if (p.next[c - 'a'] == null) {
                p.next[c - 'a'] = new Trie();
            }
            p = p.next[c - 'a'];  // will point to curr char
        }
        p.word = w;
    }
    return root;
}

private class Trie {
    Trie[] next = new Trie[26];
    String word = null;
}
}

/**
import java.util.AbstractList;

class Trie {
    Trie[] next = new Trie[26];
    String word;
}

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        return new AbstractList<String>() {
            List<String> res;

            private void init() {
                Trie root = build(words);
                Set<String> set = new HashSet<>();
                for(int i = 0; i < board.length; i++) {
                    for(int j = 0; j < board[0].length; j++) {
                        dfs(board, i, j, root, set);
                    }
                }
                res = new ArrayList<>(set);
            }

            @Override
            public int size() {
                if(res == null) 
                    init();
                return res.size();
            }

            @Override
            public String get(int index) {
                return res.get(index);
            }
        };
    }

    private void dfs(char[][] board, int i, int j, Trie p, Set<String> set) {
        char c = board[i][j];
        if(c == '#' || p.next[c-'a'] == null)
            return ;
        p = p.next[c-'a'];
        if(p.word != null) {
            set.add(p.word);
            p.word = null;
        }

        board[i][j] = '#';
        if(i > 0)   dfs(board, i-1, j, p, set);
        if(j > 0)   dfs(board, i, j-1, p, set);
        if(i < board.length - 1)    dfs(board, i+1, j, p, set);
        if(j < board[0].length-1)   dfs(board, i, j+1, p, set);
        board[i][j] = c;
    }

    private Trie build(String[] words) {
        Trie root = new Trie();
        for(String w : words) {
            Trie p = root;
            for(char c : w.toCharArray()) {
                int i = c - 'a';
                if(p.next[i] == null)
                    p.next[i] = new Trie();
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }
} */