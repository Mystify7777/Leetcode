// 3043. Find the Length of the Longest Common Prefix
// https://leetcode.com/problems/find-the-length-of-the-longest-common-prefix/
import java.util.*;

class Solution {
    public int digits(int x) {
        int cnt = 0;
        while (x > 0) {
            cnt++;
            x /= 10;
        }
        return cnt;
    }

    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        HashSet<Integer> prefixes = new HashSet<>();

        // storing all prefixes of arr1
        for (int num : arr1) {
            int x = num;
            while (x > 0) {
                prefixes.add(x);
                x /= 10;
            }
        }

        int ans = 0;

        // check prefixes of arr2 numbers
        for (int num : arr2) {
            int x = num;
            int len = digits(num);

            // checking from larger => smaller
            while (x > 0) {
                if (prefixes.contains(x)) {
                    ans = Math.max(ans, len);
                    // first match is the longest
                    // so we stop
                    break;
                }

                x /= 10;
                len--;
            }
        }

        return ans;
    }
}

// try trie but dont cry
class Solution2 {
    private class TrieNode {
        private final TrieNode[] children;

        private TrieNode() {
            children = new TrieNode[10];
        }
    }

    private TrieNode root;
    private final int[] arr = {100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10, 1};

    private void add(int x) {
        TrieNode node = root;
        for (int i = count(x); i < 9; i++) {
            if (node.children[x / arr[i]] == null) {
                node.children[x / arr[i]] = new TrieNode();
            }
            node = node.children[x / arr[i]];
            x %= arr[i];
        }
    }

    private int find(int x) {
        TrieNode node = root;
        int ans = 0;
        for (int i = count(x); i < 9; i++) {
            if (node.children[x / arr[i]] == null) {
                break;
            }
            node = node.children[x / arr[i]];
            x %= arr[i];
            ans++;
        }
        return ans;
    }

    private int count(int x) {
        for (int i = 0; i < 9; i++) {
            if (x >= arr[i]) {
                return i;
            }
        }
        return -1;
    }

    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        root = new TrieNode();
        for (int i : arr1) {
            add(i);
        }
        int ans = 0;
        for (int i : arr2) {
            ans = Math.max(ans, find(i));
        }
        return ans;
    }
}

// more trie to try
class TrieNode {
    TrieNode[] child;

    TrieNode() {
        child = new TrieNode[10];
    }
}

class Solution3 {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        TrieNode root = new TrieNode();

        for (int num : arr1) {
            insert(root, num);
        }

        int maxPrefix = 0;
        for (int num : arr2) {
            maxPrefix = Math.max(maxPrefix, search(root, num));
        }
        return maxPrefix;
    }

    private int search(TrieNode root, int num) {
        String s = String.valueOf(num);
        TrieNode node = root;
        int count = 0;
        for (char c : s.toCharArray()) {
            int d = c - '0';
            if (node.child[d] == null) {
                break;
            }
            node = node.child[d];
            count++;
        }
        return count;
    }

    private void insert(TrieNode root, int num) {
        TrieNode node = root;
        String s = String.valueOf(num);

        for (char c : s.toCharArray()) {
            int d = c - '0';
            if (node.child[d] == null) {
                node.child[d] = new TrieNode();
            }
            node = node.child[d];
        }
    }
}
