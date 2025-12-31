// 238. Product of Array Except Self
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int ans[] = new int[n];
        Arrays.fill(ans, 1);
        int curr = 1;
        for(int i = 0; i < n; i++) {
            ans[i] *= curr;
            curr *= nums[i];
        }
        curr = 1;
        for(int i = n - 1; i >= 0; i--) {
            ans[i] *= curr;
            curr *= nums[i];
        }
        return ans;
    }
}
//same but faster
/**
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] prefixProduct = new int[n];
        prefixProduct[0] = nums[0];
        for(int i=1;i<n;i++){
            prefixProduct[i] = prefixProduct[i-1]*nums[i];
        }
        int suffix = 1;
        for(int i =n-1; i>0; i--){
            prefixProduct[i] = prefixProduct[i-1]*suffix;
            suffix = suffix*nums[i];
        }
        prefixProduct[0] = suffix;
        return prefixProduct;
    }
}
 */