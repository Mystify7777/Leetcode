// 3691. Maximum Total Subarray Value II
// https://leetcode.com/problems/maximum-total-subarray-value-ii/
class Solution2 {
    int[][] max ;
    int[][] min;
    int[] log;
    int M ;

    void fillSparseTable(int[] nums){
        int n = nums.length;
        for(int i = 0;i<n;i++){
            max[i][0] = nums[i];
            min[i][0] = nums[i];
        }
        for(int j = 1;j<=M;j++){
            for(int i = 0;i+(1<<j)<=n;i++){
                max[i][j] = Math.max(max[i][j-1],
                                    max[i+(1<<(j-1))][j-1]);
                min[i][j] = Math.min(min[i][j-1],min[i+(1<<(j-1))][j-1]);
            }
        }
    }

    int query(int l,int r){
        int len = r-l+1;
        int k = log[len];
        return Math.max(max[l][k],max[r-(1<<k)+1][k])- 
              Math.min(min[l][k],min[r-(1<<k)+1][k]);
    }

    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;
        log = new int[n+1];
        log[1] = 0;
        for(int i =2;i<=n;i++)
            log[i] = log[i/2] +1;
        this.M = log[n];
        max = new int[n][M+1];
        min = new int[n][M+1];
        fillSparseTable(nums);
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>((a,b)-> b[0]-a[0]);
        for(int i = 0;i<Math.min(n,k);i++){
            heap.offer(new int[]{query(i,n-1),i,n-1});
        }
        long res = 0;
        while(k>0 && !heap.isEmpty()){
            int[] top = heap.poll();  
            res += top[0];
            if(top[2]-1>=top[1]){
                top[0] = query(top[1],top[2]-1);
                top[2]--;
                heap.offer(top);
            }  
            k--;
        }
        return res;
    }
}

class Solution {
    static final int BLOCK_SHIFT = 3;
    static final int BLOCK_SIZE = 1 << BLOCK_SHIFT; // 8
    static final int PACK_SHIFT = 16;
    static final int PACK_MASK = 0xFFFF;

    int n;
    int[] a;

    int blockCount;
    int levels;

    int[][] stMax;
    int[][] stMin;

    long[] heap;
    int heapSize;

    public long maxTotalValue(int[] nums, int k) {
        this.a = nums;
        this.n = nums.length;

        buildBlockedSparseTable(nums);

        heap = new long[n + 2];
        buildInitialHeap(nums);

        long ans = 0;

        while (k-- > 0 && heapSize > 0) {
            long top = heap[0];
            long val = top >> 32;

            if (val == 0) break;

            ans += val;

            int l = (int) ((top >> PACK_SHIFT) & PACK_MASK);
            int r = (int) (top & PACK_MASK);

            long replacement;

            if (r > l) {
                int newVal = rangeValue(l, r - 1);
                replacement = pack(newVal, l, r - 1);
            } else {
                --heapSize;
                if (heapSize == 0) break;
                replacement = heap[heapSize];
                heap[heapSize] = -1L;
            }

            replaceTop(replacement);
        }

        return ans;
    }

    private void buildBlockedSparseTable(int[] nums) {
        blockCount = (n + BLOCK_SIZE - 1) >> BLOCK_SHIFT;
        levels = 32 - Integer.numberOfLeadingZeros(blockCount);

        stMax = new int[levels][blockCount];
        stMin = new int[levels][blockCount];

        for (int b = 0; b < blockCount; b++) {
            int start = b << BLOCK_SHIFT;
            int end = start + BLOCK_SIZE;
            if (end > n) end = n;

            int mx = nums[start];
            int mn = nums[start];

            for (int i = start + 1; i < end; i++) {
                int v = nums[i];
                if (v > mx) mx = v;
                if (v < mn) mn = v;
            }

            stMax[0][b] = mx;
            stMin[0][b] = mn;
        }

        for (int j = 1; j < levels; j++) {
            int half = 1 << (j - 1);
            int len = 1 << j;
            int limit = blockCount - len;

            int[] prevMax = stMax[j - 1];
            int[] currMax = stMax[j];
            int[] prevMin = stMin[j - 1];
            int[] currMin = stMin[j];

            for (int i = 0; i <= limit; i++) {
                int x = prevMax[i];
                int y = prevMax[i + half];
                currMax[i] = x > y ? x : y;

                x = prevMin[i];
                y = prevMin[i + half];
                currMin[i] = x < y ? x : y;
            }
        }
    }

    private int rangeValue(int l, int r) {
        int bl = l >> BLOCK_SHIFT;
        int br = r >> BLOCK_SHIFT;

        int mx = a[l];
        int mn = a[l];

        if (bl == br) {
            for (int i = l + 1; i <= r; i++) {
                int v = a[i];
                if (v > mx) mx = v;
                if (v < mn) mn = v;
            }

            return mx - mn;
        }

        int leftEnd = (bl + 1) << BLOCK_SHIFT;

        for (int i = l + 1; i < leftEnd; i++) {
            int v = a[i];
            if (v > mx) mx = v;
            if (v < mn) mn = v;
        }

        int rightStart = br << BLOCK_SHIFT;

        for (int i = rightStart; i <= r; i++) {
            int v = a[i];
            if (v > mx) mx = v;
            if (v < mn) mn = v;
        }

        int fl = bl + 1;
        int fr = br - 1;

        if (fl <= fr) {
            int len = fr - fl + 1;
            int j = 31 - Integer.numberOfLeadingZeros(len);
            int rs = fr - (1 << j) + 1;

            int x = stMax[j][fl];
            int y = stMax[j][rs];
            int midMax = x > y ? x : y;
            if (midMax > mx) mx = midMax;

            x = stMin[j][fl];
            y = stMin[j][rs];
            int midMin = x < y ? x : y;
            if (midMin < mn) mn = midMin;
        }

        return mx - mn;
    }

    private void buildInitialHeap(int[] nums) {
        heapSize = n;

        int sfxMax = nums[n - 1];
        int sfxMin = nums[n - 1];

        heap[n - 1] = pack(0, n - 1, n - 1);

        for (int i = n - 2; i >= 0; i--) {
            int v = nums[i];

            if (v > sfxMax) sfxMax = v;
            if (v < sfxMin) sfxMin = v;

            heap[i] = pack(sfxMax - sfxMin, i, n - 1);
        }

        heap[n] = -1L;

        for (int i = (n >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private long pack(long val, int l, int r) {
        return (val << 32) | ((long) l << PACK_SHIFT) | r;
    }

    private void replaceTop(long replacement) {
        int idx = 0;

        while (true) {
            int child = (idx << 1) + 1;

            if (child >= heapSize) break;

            int right = child + 1;

            if (right < heapSize && heap[right] > heap[child]) {
                child = right;
            }

            if (heap[child] > replacement) {
                heap[idx] = heap[child];
                idx = child;
            } else {
                break;
            }
        }

        heap[idx] = replacement;
    }

    private void siftDown(int idx) {
        long v = heap[idx];

        while (true) {
            int child = (idx << 1) + 1;

            if (child >= heapSize) break;

            int right = child + 1;

            if (right < heapSize && heap[right] > heap[child]) {
                child = right;
            }

            if (heap[child] > v) {
                heap[idx] = heap[child];
                idx = child;
            } else {
                break;
            }
        }

        heap[idx] = v;
    }
}