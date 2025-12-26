//477. Total Hamming Distance

//I need a really good explanation as it is getting above my head
class Solution {
    public int totalHammingDistance(int[] nums) {
         int result = 0;
        for(int i = 0; i<32; i++){
              int count_ones= 0;
            for(int j = 0; j<nums.length; j++){
                count_ones+=(nums[j]>>i)&1; //count no. of 1's at a specific position
            }
            result +=(count_ones*(nums.length-count_ones));
        }
        return result;  
    }
}

//Okay so what happened?
/**
class Solution {
    public int totalHammingDistance(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int distance = 0;
        for (int i = 0; i < 32; i++) {
            int one_count = 0;
            for (int j = 0; j < nums.length; j++) {
                one_count += (nums[j] >> i) & 1;
            }
            distance += one_count * (nums.length - one_count);
        }
        return distance;
    }
}
 */

// another approach

/**
 * class Solution {
    public int totalHammingDistance(int[] nums) {
        int res = 0;
        for (int bit = 0; bit < 32; bit++) {
            res += distance(nums, bit);
        }
        return res;
    }
    private int distance(int[] nums, int bit) {
        int ones = 0;
        for (int num : nums) {
            ones += ((num >> bit) & 1);
        }
        return ones * (nums.length - ones);
    }
}
 */