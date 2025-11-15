// 3234. Count the Number of Substrings With Dominant Ones
//https://leetcode.com/problems/count-the-number-of-substrings-with-dominant-ones/
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length(); 
        int[] prefix = new int[n]; //array to store prefix sums of '1's

        prefix[0] = ((int)(s.charAt(0) - '0')) == 1 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + (((int)(s.charAt(i) - '0')) == 1 ? 1 : 0);
        }

        int ans = 0;

        
        for (int i = 0; i < n; i++) { //i => starting index of substring

            int one = 0; // Count of '1's in the current substring
            int zero = 0; // Count of '0's in the current substring

            for (int j = i; j < n; j++) { // j=> ending index of current substring

                one = prefix[j] - (i == 0 ? 0 : prefix[i - 1]);
                zero = (j - i + 1) - one;
              
                //CASE->1
                if ((zero * zero) > one) { // Not dominant
                    j += (zero * zero - one - 1);
                } 
                //CASE->2
                else if ((zero * zero) == one) { //just this one is dominant
                    ans++; 
                } 
                //CASE->3
                else if ((zero * zero) < one) { 
                    ans++; 
                    // Calculate the difference to determine how far to skip forward
                    int diff = (int) Math.sqrt(one) - zero;
                    int nextj = j + diff; // Determine the next position to skip to

                    if (nextj >= n) {
                        ans += (n - j - 1);
                    } else {
                        ans += diff; 
                    }

                    j = nextj; // Update j to the next position
                }
            }
        }
        return ans; // Return the final answer
    }
}

//Alternate faster method
/**
class Solution {
  public int numberOfSubstrings(String s) {
    return prefixEnumeration(s);
  }

  private int prefixEnumeration(String s) {
    int n = s.length();

    int[] zerosIdx = new int[n + 1];
    int nextZeroIdx = 1;
    zerosIdx[0] = -1;

    int totalOnes = 0;

    int res = 0;

    for(int right = 0; right < n; right++) {
      if(s.charAt(right) == '0') {
        zerosIdx[nextZeroIdx++] = right;
      } else {
        res += right - zerosIdx[nextZeroIdx - 1];
        totalOnes++;
      }

      for(int zeroPos = nextZeroIdx - 1; zeroPos > 0 && (nextZeroIdx - zeroPos) * (nextZeroIdx - zeroPos) <= totalOnes; zeroPos--) {
        int zerosCount = nextZeroIdx - zeroPos; //nextZeroIdx - 1 (zeros at right pos) - zeroes at earlier pos + 1

        int onesCount = right - zerosIdx[zeroPos] + 1 - zerosCount; //totalLength - zerosCount

        int onesDeficit = zerosCount * zerosCount - onesCount; //how many ones we're missing within [zeroIdx[z - 1]+1, zeroIdx[z]] range to satisfy
                                                                //the condition - therefore, how many substrings starting farther to the right
                                                                //(closer to zPos) would not satisfy it

        int extendable = zerosIdx[zeroPos] - zerosIdx[zeroPos - 1]; //how far back are we looking now - essentially, how many positions can we 
                                                                    //start our substring from

        res += Math.max(extendable - Math.max(onesDeficit, 0), 0); //all the possible substrings (extendable) decreased by the number of 
                                                                    //invalid substrings (those starting further to the right, and therefore
                                                                    // having less ones)

      }
    }

    return res;
  }
} */