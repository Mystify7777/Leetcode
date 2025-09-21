// 17. Letter Combinations of a Phone Number
//older soln
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        
        if (digits == null || digits.length() == 0) {
            return res;
        }
        
        Map<Character, String> digitToLetters = new HashMap<>();
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
        
        backtrack(digits, 0, new StringBuilder(), res, digitToLetters);
        
        return res;        
    }

    private void backtrack(String digits, int idx, StringBuilder comb, List<String> res, Map<Character, String> digitToLetters) {
        if (idx == digits.length()) {
            res.add(comb.toString());
            return;
        }
        
        String letters = digitToLetters.get(digits.charAt(idx));
        for (char letter : letters.toCharArray()) {
            comb.append(letter);
            backtrack(digits, idx + 1, comb, res, digitToLetters);
            comb.deleteCharAt(comb.length() - 1);
        }
    }    
}
//why is this so slow?
/*
import java.util.*;

class Solution {
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) return new ArrayList<>();
        return pd("", digits);
    }

    private List<String> pd(String p, String up) {
        if (up.isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(p);
            return list;
        }

        int digit = up.charAt(0) - '0';

        // If digit outside 2..9, skip it and continue with remaining digits
        if (digit < 2 || digit > 9) {
            return pd(p, up.substring(1));
        }

        int start = (digit - 2) * 3; // base index in alphabet ('a' -> 0)
        int end = start + 3;        // exclusive

        if (digit == 7) {           // pqrs
            end = start + 4;
        } else if (digit == 8) {    // tuv
            start = 19; // index of 't'
            end = 22;
        } else if (digit == 9) {    // wxyz
            start = 22; // index of 'w'
            end = 26;   // exclusive after 'z'
        }

        List<String> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            char ch = (char) ('a' + i);
            result.addAll(pd(p + ch, up.substring(1)));
        }
        return result;
    }
}
*/