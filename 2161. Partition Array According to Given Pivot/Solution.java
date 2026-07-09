// 2161. Partition Array According to Given Pivot
// https://leetcode.com/problems/partition-array-according-to-given-pivot/
class Solution2 {
    public int[] pivotArray(int[] nums, int pivot) {
        int smaller = 0;
        int larger = 0;
        int equal = 0;
        
        int n = nums.length;
        
        for (int num : nums){
            if(num > pivot){
                larger++;
            }else if(num < pivot){
                smaller++;
            }else{
                equal++;
            }
        }
        
        int i = 0;
        int j = smaller;
        int k = smaller + equal;
        
        int[] answer = new int[n];
        
        for (int num : nums){
            if(num > pivot){
                answer[k] = num;
                k++;
            }else if(num < pivot){
                answer[i] = num;
                i++;
            }else{
                answer[j] = num;
                j++;
            }
        }
        
        return answer;
    }
}

class Solution {
    static {for(int i=0; i<300;i++) pivotArray(new int[1],0);}
    public static int[] pivotArray(int[] nums, int pivot) {
        int ans[] = new int[nums.length];
        int l=0,r=nums.length-1;
        for(int i=0,j=nums.length-1; i<nums.length; i++,j--){
            if(nums[i]<pivot) ans[l++]=nums[i];
            if(nums[j]>pivot) ans[r--]=nums[j];

        }
        while(l<=r) ans[l++]= pivot;
        return ans;
    }
}