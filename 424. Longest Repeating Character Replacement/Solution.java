// 424. Longest Repeating Character Replacement
class Solution {
    public int characterReplacement(String s, int k) {
        HashMap<Character, Integer> freqs = new HashMap<>();
        int res = 0, i = 0, maxFreq = 0;

        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            freqs.put(c, freqs.getOrDefault(c, 0) + 1);
            maxFreq = Math.max(maxFreq, freqs.get(c));

            while ((j - i + 1) - maxFreq > k) {
                char left = s.charAt(i);
                freqs.put(left, freqs.get(left) - 1);
                i++;
            }

            res = Math.max(res, j - i + 1);
        }

        return res;
    }
}


// Why is this implementation faster?
// ANSWER:
// 1. Array Access: int[] is O(1) direct memory access, HashMap requires hashCode computation
// 2. Memory Locality: Arrays have contiguous memory → better CPU cache performance
// 3. No Boxing: int[] uses primitives, HashMap uses Integer objects (boxing overhead)
// 4. char[]: Converting to char[] once allows faster indexed access vs repeated s.charAt()
// 5. Smaller Footprint: int[26] = 104 bytes vs HashMap with entry objects and buckets
// Result: 3-5x faster performance with array implementation!

/**
class Solution {

     public int characterReplacement(String s, int k) {

        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] characters = s.toCharArray();

        int left = 0;
        int maxFreq = 0;
        int[] freq = new int[26];
        int maxLen = 0;

        for(int right = 0; right < characters.length; right++) {

            freq[characters[right] - 'A']++;

            maxFreq = Math.max(maxFreq, freq[characters[right] - 'A']);

            while (right - left + 1 - maxFreq > k) {
                // daca dupa k caractere ar veni un cracter care face parte
                // din maxfreq adicaa sa zice aaaabbaa - atunci chiar dacaa adun la max freq pariti lui a, va afi scsazut din contiditie, pt ca maresc si right
                // maresc ssi frecventaa, si se scad una pe alta, nu se scad in momentul in car eama un caracter diferit de cel apart in max freq
                freq[characters[left] - 'A']--;
                left++;
            }

            // aici ajunge doar cand se pastreaza dinstanta maxFreq + k
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

}
 */