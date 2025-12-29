// 438. Find All Anagrams in a String
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int[] freq1 = new int[26]; // Frequency of current window in 's'
        int[] freq2 = new int[26]; // Frequency of characters in 'p'
        List<Integer> list = new ArrayList<>();

        // If 's' is shorter than 'p', no anagrams possible
        if (s.length() < p.length())
            return list;

        // Initialize frequency arrays with the first 'p.length()' characters
        for (int i = 0; i < p.length(); i++) {
            freq1[s.charAt(i) - 'a']++;
            freq2[p.charAt(i) - 'a']++;
        }

        int start = 0, end = p.length();

        // Check if initial window is an anagram
        if (Arrays.equals(freq1, freq2))
            list.add(start);

        // Slide the window over the rest of 's'
        while (end < s.length()) {
            freq1[s.charAt(start) - 'a']--; // Remove leftmost char of previous window
            freq1[s.charAt(end) - 'a']++;   // Add new char to current window

            start++;
            end++;

            // If window frequencies match, it's an anagram
            if (Arrays.equals(freq1, freq2))
                list.add(start);
        }

        return list;
    }
}

//Sliding Window with Match Count
class Solution2 {
    public List<Integer> findAnagrams(String s, String p) {
        int[] freq = new int[26];
        List<Integer> result = new ArrayList<>();
        
        if (s.length() < p.length()) return result;
        
        // Build frequency map for p (positive values)
        for (char c : p.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Track how many characters have matching frequency (freq[i] == 0)
        // Initially, all letters not in p have freq == 0, so they match
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) matches++;
        }
        
        // Process first window
        for (int i = 0; i < p.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (freq[idx] == 0) matches--;       // Was perfect (not in p), now we have extra
            freq[idx]--;                          // Decrease because we found this char in window
            if (freq[idx] == 0) matches++;        // Perfect match for this char
        }
        
        // If all 26 letters match (freq == 0 for all), we found an anagram
        if (matches == 26) result.add(0);
        
        // Slide the window
        for (int i = p.length(); i < s.length(); i++) {
            // Add new character on right side
            int rightIdx = s.charAt(i) - 'a';
            if (freq[rightIdx] == 0) matches--;   // Was perfect, now changing
            freq[rightIdx]--;
            if (freq[rightIdx] == 0) matches++;   // Now perfect
            
            // Remove old character on left side
            int leftIdx = s.charAt(i - p.length()) - 'a';
            if (freq[leftIdx] == 0) matches--;    // Was perfect, now changing
            freq[leftIdx]++;
            if (freq[leftIdx] == 0) matches++;    // Now perfect
            
            // Check if current window is an anagram
            if (matches == 26) result.add(i - p.length() + 1);
        }
        
        return result;
    }
}