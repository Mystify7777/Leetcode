// 205. Isomorphic Strings
class Solution {
    public boolean isIsomorphic(String s, String t) {

        int map1[]=new int[200];
        int map2[]=new int[200];

        if(s.length()!=t.length())
            return false;


        for(int i=0;i<s.length();i++)
        {
            if(map1[s.charAt(i)]!=map2[t.charAt(i)])
                return false;

            map1[s.charAt(i)]=i+1;
            map2[t.charAt(i)]=i+1;
        }
        return true;
    }
}

//is this one a better implementation?
/**

class Solution {
    public boolean isIsomorphic(String s, String t) {

        int len = s.length();
        if (s.length() != t.length())
            return false;
        char[] seen = new char[128]; // ASCII range

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            if (seen[c] == 0) { // not mapped yet
                // check if t[i] is already mapped to another char
                for (char ch : seen) {
                    if (ch == t.charAt(i)) {
                        return false;
                    }
                }
                seen[c] = t.charAt(i);
            } else if (seen[c] != t.charAt(i)) {
                return false;
            }
        }
        return true;
        // return ;
    }
} */