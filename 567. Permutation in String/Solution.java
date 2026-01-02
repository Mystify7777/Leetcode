
// 567. Permutation in String
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        HashMap<Character, Integer> s1Count = new HashMap<>();
        HashMap<Character, Integer> s2Count = new HashMap<>();
        
        for (int i = 0; i < s1.length(); i++) {
            s1Count.put(s1.charAt(i), s1Count.getOrDefault(s1.charAt(i), 0) + 1);
            s2Count.put(s2.charAt(i), s2Count.getOrDefault(s2.charAt(i), 0) + 1);
        }
        
        if (s1Count.equals(s2Count)) {
            return true;
        }
        
        int left = 0;
        for (int right = s1.length(); right < s2.length(); right++) {
            char charRight = s2.charAt(right);
            s2Count.put(charRight, s2Count.getOrDefault(charRight, 0) + 1);
            
            char charLeft = s2.charAt(left);
            s2Count.put(charLeft, s2Count.get(charLeft) - 1);
            if (s2Count.get(charLeft) == 0) {
                s2Count.remove(charLeft);
            }
            
            left++;
            
            if (s1Count.equals(s2Count)) {
                return true;
            }
        }
        
        return false;        
    }
}

//compare
/**
class Solution {
    public boolean checkInclusion(String s1, String s2) {        
        int n = s1.length();
        int m = s2.length();
        if(n>m)
            return false;

        int s1_count[] = new int [26];
        char s2a[] = s2.toCharArray();
        for(int i=0;i<n;i++)
            s1_count[s1.charAt(i)-'a']++;
        
        int win_count[] = new int[26];
        int l=0;
        int r=0;
        while(r-l<n)
            win_count[s2a[r++]-'a']++;
        while(r<m){
            if(count_equal(s1_count,win_count))
                return true;
                win_count[s2a[l++]-'a']--;
            win_count[s2a[r++]-'a']++;
        }
        if(count_equal(s1_count,win_count))
            return true;
        return false;    
    }
    private boolean count_equal(int a[], int b[])
    {
        for(int i=0;i<26;i++)
            if(a[i]!=b[i])
                return false;

        return true;
    }
}
 */