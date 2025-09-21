
// 128. Longest Consecutive Sequence

/*class Solution {
 /*   public int longestConsecutive(int[] nums) {int result = 0;
        if (nums.length > 0) {
            if (nums.length < 1000) {
                Arrays.sort(nums);
                int current = 0;
                for (int i = 1; i < nums.length; i++) {
                    if (nums[i] != nums[i - 1]) {
                        if (nums[i] - nums[i - 1] == 1) {
                            current++;
                        } else {
                            if (current + 1 > result) {
                                result = current + 1;
                            }
                            current = 0;
                        }
                    }
                }
                if (current + 1 > result) {
                    result = current + 1;
                }
            } else {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (int num : nums) {
                    if (num > max) {
                        max = num;
                    }
                    if (num < min) {
                        min = num;
                    }
                }
                byte[] bits = new byte[max - min + 1];
                for (int num : nums) {
                    bits[num - min] = 1;
                }
                int current = 0;
                for (byte bit : bits) {
                    if (bit > 0) {
                        current++;
                    } else {
                        if (current > result) {
                            result = current;
                        }
                        current = 0;
                    }
                }
                if (current > result) {
                    result = current;
                }
            }
        }
        return result;
    }
    *//*
    public int longestConsecutive(int[] nums) {
  int max = 0;
  
  Set<Integer> set = new HashSet<Integer>();
  for (int i = 0; i < nums.length; i++) {
    set.add(nums[i]);
  }
  
  for (int i = 0; i < nums.length; i++) {
    int count = 1;
    
    // look left
    int num = nums[i];
    while (set.contains(--num)) {
      count++;
      set.remove(num);
    }
    
    // look right
    num = nums[i];
    while (set.contains(++num)) {
      count++;
      set.remove(num);
    }
    
    max = Math.max(max, count);
  }
  
  return max;
}
}
*/

import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if(nums.length == 0) return 0;  

        Arrays.sort(nums);          
        int maxLen = 1;                  
        int currLen = 1;                

        for(int i = 0; i < nums.length - 1; i++) {  
            if(nums[i] == nums[i+1]) {              
                continue;
            } 
            if(nums[i+1] == nums[i] + 1) {          
                currLen++;
            } else {                               
                maxLen = Math.max(maxLen, currLen);
                currLen = 1;
            }
        }

        return Math.max(maxLen, currLen);  
    }
}