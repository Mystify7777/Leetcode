// 387. First Unique Character in a String
/*
class Solution {
     public int firstUniqChar(String s) {
        // Stores lowest index / first index
        int ans = Integer.MAX_VALUE;
        // Iterate from a to z which is 26 which makes it constant
        for(char c='a'; c<='z';c++){
            // indexOf will return first index of alphabet and lastIndexOf will return last index
            // if both are equal then it has occured only once.
            // through this we will get all index's which are occured once
            // but our answer is lowest index
            int index = s.indexOf(c);
            if(index!=-1&&index==s.lastIndexOf(c)){
                ans = Math.min(ans,index);
            }
        }

        // If ans remain's Integer.MAX_VALUE then their is no unique character
        return ans==Integer.MAX_VALUE?-1:ans;
    }   
}
*/

// class Solution {
//     public int firstUniqChar(String s) {
        
//         int[] seen = new int[26];

//         for(char c : s.toString())
//             seen[c - 'a']++;

//         for(char c : s.toString()){
//             if(seen[c - 'a'] == 1)
//                 return s.indexOf(c);
//         }

//         return -1;

//     }
// }


class Solution {
    public int firstUniqChar(String s) {
        
        char[] let = s.toCharArray();
        int[] seen = new int[26];

        for(int i = 0; i < let.length; i++)
            seen[let[i] - 'a']++;

        for(int i = 0; i < let.length; i++){
            if(seen[let[i] - 'a'] == 1)
                return i;
        }

        return -1;

    }
}