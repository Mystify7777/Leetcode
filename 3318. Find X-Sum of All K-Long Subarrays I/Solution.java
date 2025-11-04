import java.util.*;
//3318. Find X-Sum of All K-Long Subarrays I
//https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-i/
class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[Math.max(0, n - k + 1)];
        Map<Integer, Integer> freq = new HashMap<>();

        for (int i = 0; i < k; i++) {
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
        }

        ans[0] = computeXSum(freq, x);

        for (int i = k; i < n; i++) {
            int add = nums[i];
            int rem = nums[i - k];

            freq.put(add, freq.getOrDefault(add, 0) + 1);
            int fr = freq.get(rem) - 1;
            if (fr == 0) freq.remove(rem);
            else freq.put(rem, fr);

            ans[i - k + 1] = computeXSum(freq, x);
        }

        return ans;
    }

    private int computeXSum(Map<Integer, Integer> freq, int x) {
        List<int[]> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            items.add(new int[]{e.getKey(), e.getValue()});
        }
        items.sort((a, b) -> {
            if (a[1] != b[1]) return b[1] - a[1];
            return b[0] - a[0];
        });
        long sum = 0;
        int take = Math.min(x, items.size());
        for (int i = 0; i < take; i++) {
            sum += 1L * items.get(i)[0] * items.get(i)[1];
        }
        return (int) sum;
    }
}

//Alternate faster approach
/**
class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < result.length; i++) {
            int left = i, right = i + k - 1;
            result[i] = findXSumofSubArray(nums, left, right, x);
        }

        return result;
    }

    private int findXSumofSubArray(int[] nums, int left, int right, int x) {
        int sum = 0, distinctCount = 0;
        int[] freq = new int[51]; 

        for (int i = left; i <= right; i++) {
            sum += nums[i];
            if (freq[nums[i]] == 0) {
                distinctCount++;
            }
            freq[nums[i]]++;
        }

        if (distinctCount < x) {
            return sum;
        }

        sum = 0;
        for (int pick = 0; pick < x; pick++) {
            int bestFreq = -1;
            int bestVal = -1;

            for (int val = 50; val >= 1; val--) {
                if (freq[val] > bestFreq) {
                    bestFreq = freq[val];
                    bestVal = val;
                }
            }

            if (bestVal != -1) {
                sum += bestVal * bestFreq;
                freq[bestVal] = 0;
            }
        }
        
        return sum;
    }
}
// TC : O(n - k + 1) * findXSumofSubArray() => O(n) * findXSumofSubArray()
// findXSumofSubArray() =>  O(k + 50 * x) ~ O(k + x)
// Total TC : O(n * (k + x))
// SC : O(n - k + 1) * O(51) ~ O(n)
 */