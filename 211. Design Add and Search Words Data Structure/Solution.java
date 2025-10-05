// 211. Design Add and Search Words Data Structure
// https://leetcode.com/problems/design-add-and-search-words-data-structure/
class WordDictionary {
    private WordDictionary[] children;
    boolean isEndOfWord;
    // Initialize your data structure here. 
    public WordDictionary() {
        children = new WordDictionary[26];
        isEndOfWord = false;
    }
    
    // Adds a word into the data structure. 
    public void addWord(String word) {
        WordDictionary curr = this;
        for(char c: word.toCharArray()){
            if(curr.children[c - 'a'] == null)
                curr.children[c - 'a'] = new WordDictionary();
            curr = curr.children[c - 'a'];
        }
        curr.isEndOfWord = true;
    }
    
    // Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. 
    public boolean search(String word) {
        WordDictionary curr = this;
        for(int i = 0; i < word.length(); ++i){
            char c = word.charAt(i);
            if(c == '.'){
                for(WordDictionary ch: curr.children)
                    if(ch != null && ch.search(word.substring(i+1))) return true;
                return false;
            }
            if(curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return curr != null && curr.isEndOfWord;
    }
}
/**
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isWord = false;
}

class WordDictionary {
    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode cur = root;
        for( char c : word.toCharArray() ){
            int i = c - 'a';
            if( cur.children[i] == null ){
                cur.children[i] = new TrieNode();
            }
            cur = cur.children[i];
        }
        cur.isWord = true;
    }
    
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int j, TrieNode root){
        TrieNode cur = root;

        for( int i = j ; i < word.length() ; i++ ){
            char c = word.charAt(i);
            if( c == '.' ){
                for( TrieNode child : cur.children ){
                    if( child != null && dfs(word, i+1, child) ){
                        return true;
                    }
                }
                return false;
            } else {
                if( cur.children[ c - 'a' ] == null ){
                    return false;
                }
                cur = cur.children[ c - 'a' ];
            }
        }

        return cur.isWord;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */ 