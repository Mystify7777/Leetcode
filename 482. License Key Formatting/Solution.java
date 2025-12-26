// 482. License Key Formatting
class Solution {
    public String licenseKeyFormatting(String S, int K) {
        
        StringBuilder sb = new StringBuilder();

        for (int i = S.length() - 1, count = 0 ; i >= 0 ; --i) {
            
            char c = S.charAt(i);
            if (c == '-') continue;

            // put a '-' first if we already append K characters
            if (count == K) {
                sb.append('-');
                count = 0;
            }

            sb.append(Character.toUpperCase(c));
            ++count;
        }

        return sb.reverse().toString();
    }
}

//what made this code run faster

/**
class Solution {
    public String licenseKeyFormatting(String str, int k) {
        char[] s = str.toCharArray();
        int p = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] != '-') {
                s[p++] = Character.toUpperCase(s[i]);
            }
        }
        if (p == 0) return "";
        StringBuilder sb = new StringBuilder();
        int firstPartLen = p % k;
        if (firstPartLen == 0) firstPartLen = k;
        sb.append(s, 0, firstPartLen);
        // System.out.println("firstp" + firstPartLen + " p: " + p);
        for (int i = firstPartLen; i < p; i += k) {
            sb.append('-').append(s, i, k);
        }
        return sb.toString();
    }
}
 */