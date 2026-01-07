// 239. Sliding Window Maximum239. Sliding Window Maximum
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
  // assume nums is not null
  int n = nums.length;
  if (n == 0 || k == 0) {
    return new int[0];
  }
  int[] result = new int[n - k + 1]; // number of windows
  Deque<Integer> win = new ArrayDeque<>(); // stores indices
  
  for (int i = 0; i < n; ++i) {
    // remove indices that are out of bound
    while (win.size() > 0 && win.peekFirst() <= i - k) {
      win.pollFirst();
    }
    // remove indices whose corresponding values are less than nums[i]
    while (win.size() > 0 && nums[win.peekLast()] < nums[i]) {
      win.pollLast();
    }
    // add nums[i]
    win.offerLast(i);
    // add to result
    if (i >= k - 1) {
      result[i - k + 1] = nums[win.peekFirst()];
    }
  }
  return result;
}
}

class Solution2 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length-k+1];
        int[] pre = new int[nums.length];
        int[] suf = new int[nums.length];

        for(int i = 0; i <nums.length; i++){
            if(i%k==0) pre[i] = nums[i]; 
            else pre[i] = Math.max(pre[i-1], nums[i]);
        }

        suf[nums.length-1] = nums[nums.length-1];
        for(int i = nums.length-2; i >=0; i--){
            if((i+1)%k==0) suf[i] = nums[i]; 
            else suf[i] = Math.max(suf[i+1], nums[i]);
        }

        for(int i = 0; i < ans.length; i++){
            ans[i] = Math.max(pre[i+k-1], suf[i]);
        }
        return ans;
    }
}
// is it optimized?
// why is it faster?