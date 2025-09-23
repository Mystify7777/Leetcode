// 560. Subarray Sum Equals K
import java.util.HashMap;
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap < Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
      
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}

/**
Someone explain what the heck is this code:
class Solution {
    private static final int NULL = Integer.MIN_VALUE, MIXER = 0x9E3779BA;
    public static int subarraySum(int[] nums, final int k){
        final int mask = mask(nums.length);
        final int[] hashtable = new int[mask + 1];
        int res = 0, sum = 0, zeros = 1;
        for(final int n : nums){
            sum += n;
            final int diff = sum - k;
            if(diff != 0){
                int i = diff * MIXER & mask;
                while(true){
                    final int key = hashtable[i];
                    if(key == 0) break;
                    if(key == diff){
                        res += hashtable[i+1];
                        break;
                    }
                    i = i + 2 & mask;
                }
            }else{
            res += zeros;
            }
            if(sum != 0){
                int i = sum * MIXER & mask;
                while(true){
                    final int key = hashtable[i];
                    if(key == 0){
                        hashtable[i] = sum;
                        hashtable[i + 1] = 1;
                        break;
                    }
                    if(key == sum){
                        hashtable[i+1]++;
                        break;
                    }
                    i = i + 2 & mask;
                }
            }else{
            zeros++;
        }
    }
    return res;
    }
    public static int mask(int n){
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >>8;
        return (n << 1) | 31;
    }
}
 */