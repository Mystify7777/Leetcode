// 290. Word Pattern
// https://leetcode.com/problems/word-pattern/

// class Solution {
//    public boolean wordPattern(String pattern, String str) {
//     String[] words = str.split(" ");
//     if (words.length != pattern.length())
//         return false;
//     Map index = new HashMap();
//     for (Integer i=0; i<words.length; ++i)
//         if (index.put(pattern.charAt(i), i) != index.put(words[i], i))
//             return false;
//     return true;
// }
// }
// ---
class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        
        if (pattern.length() != words.length) {
            return false;}

        HashMap<Character, String> charToWord = new HashMap<>();
        HashSet<String> seenWords = new HashSet<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];

            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(w)) {
                    return false;}
            } else {
                if (seenWords.contains(w)) {
                    return false;}
                charToWord.put(c, w);
                seenWords.add(w);}}
        return true;
    }
}