// 3075. Maximize Happiness of Selected Children
//https://leetcode.com/problems/maximize-happiness-of-selected-children/
public class Solution {
    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);
        long res = 0;
        int n = happiness.length, j = 0;

        for (int i = n - 1; i >= n - k; --i) {
            happiness[i] = Math.max(happiness[i] - j++, 0);
            res += happiness[i];
        }

        return res;
    }
}


//Alternate approach - why is this so complex?
/**
class Solution {
    public long maximumHappinessSum(int[] happiness, int k) {
        int n = happiness.length;
        quickselect(happiness, 0, n - 1, n - k);

        long ans = 0L;
        if(happiness[n - k] < k - 1) {
            Arrays.sort(happiness, n - k + 1, n);
            for(int i = 0; i < k; ++i) {
                if(happiness[n - 1 - i] <= i) return ans - i * (i - 1L) / 2L;
                ans += happiness[n - 1 - i];
            }
        }
        for(int i = n - k; i < n; ++i) ans += happiness[i];
        return ans - k * (k - 1L) / 2L;
    }
    private void quickselect(int[] nums, int l, int r, int k) {
        int current = nums[l + r >>> 1], left = l, right = r;
        while(left <= right) {
            if(nums[left] < current) left++;
            else if(nums[right] > current) right--;
            else {
                int temp = nums[left];
                nums[left++] = nums[right];
                nums[right--] = temp;
            }
        }
        if(right >= k) quickselect(nums, l, right, k);
        else if(left <= k) quickselect(nums, left, r, k);
    }
}
 */