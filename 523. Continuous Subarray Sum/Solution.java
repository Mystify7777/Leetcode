// 523. Continuous Subarray Sum

class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {      
        // maintain a hash map to store <sum % k, index>
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sum %= k; 
            // case 1
            if (sum == 0 && i > 0) {
                return true;
            }
            // case 2
            if (map.containsKey(sum) && i - map.get(sum) > 1) { 
                return true;
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i); 
            }
            
        }
        return false;
    }
}
// ^^^
//Explain it please.. didn't understand


//Alternate approach -->
/**
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        int n=nums.length;

        int p[] = new int[n];
        p[0] = nums[0];
        for(int i=1; i<n ;i++) {
            p[i] = p[i-1] + nums[i];
        }

        for(int r= 1; r<n ;r++) {
            if(nums[r] == 0 && nums[r-1] == 0) return true;

            if(p[r]%k == 0) return true;

            for(int l = r - 2; l>=0 && p[r]-p[l]>=k; l--) {
                if((p[r] - p[l])%k ==0) {
                    return true;
                }
            }

        }

        

        return false;
    }
}
 */