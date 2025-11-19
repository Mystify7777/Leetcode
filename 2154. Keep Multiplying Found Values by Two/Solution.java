// 2154. Keep Multiplying Found Values by Two
//https://leetcode.com/problems/keep-multiplying-found-values-by-two/description/
class Solution {
    public int findFinalValue(int[] nums, int k) {
        int bits = 0;
        for (int num : nums) {
            if (num % k != 0) continue;
            int n = num / k;
            if ((n & (n - 1)) == 0)
                bits |= n;
        }
        bits++;
        return k * (bits & -bits);
    }
}

//another method
/**
class Solution {
    public int findFinalValue(int[] nums, int original) {
        boolean x=true;
        while(x){
            x=found(nums,original);
            original*=2;
        }
        return original/2;
        
    }
    public boolean found(int [] nums,int var){
        for(int ele:nums){
            if(ele==var){
                return true;
            }
        }
        return false;
    }
}
 */