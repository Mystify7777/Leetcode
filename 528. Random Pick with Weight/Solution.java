// 528. Random Pick with Weight
// class Solution {
    
//     private ArrayList<Integer> nums;
//     private Random rand;

//     public Solution(int[] w) {
//         this.nums = new ArrayList<>();
//         this.rand = new Random();
        
//         for (int i = 0; i < w.length; i++)
//             for (int j = 0; j < w[i]; j++)
//                 this.nums.add(i);
//     }
    
//     public int pickIndex() {
//         int n = this.rand.nextInt(nums.size());
//         return nums.get(n);
//     }
// }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */

/*
 class Solution {
    
    private int[] nums;
    private int total;
    private Random rand;

    public Solution(int[] w) {
        this.nums = new int[w.length];
        this.rand = new Random();
        
        int runningTotal = 0;
        for (int i = 0; i < w.length; i++) {
            runningTotal += w[i];
            this.nums[i] = runningTotal;
        }
        
        this.total = runningTotal;
    }
    
    public int pickIndex() {
        // no numbers to pick!
        if (this.total == 0)
            return -1;
        
        int n = this.rand.nextInt(this.total);
        for (int i = 0; i < this.nums.length; i++) {
            if (n < this.nums[i])
                return i;
        }
        
        return - 1;
    }
}
*/
/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */
/**
class Solution {
    int[] prefixSumArr;

    int max;
    Random random = new Random();

    public Solution(int[] w) {
        prefixSumArr = new int[w.length];
        int prefixSum = 0;

        for (int i = 0; i < w.length; i++) {
            prefixSum += w[i];
            prefixSumArr[i] = prefixSum;
        }

        max = prefixSumArr[prefixSumArr.length-1];
    }
    
    public int pickIndex() {
        int target = max == 1 ? 1 : random.nextInt(1, max + 1);

        int left = 0;
        int right = prefixSumArr.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prefixSumArr[mid] == target) {
                return mid;
            }
            else if (target > prefixSumArr[mid]) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }

        return left;
    }
}


 */
 /**
 
 class Solution {

    int[] w;
    int index = 0;
    int value = 0;

    public Solution(int[] w) {
        double total = 0.0;
        for(int val: w)
        {
            total += val;
        }
        for(int i=0;i<w.length;i++)
        {
            int prop = (int)Math.ceil((w[i]/total)*100);
            w[i] = prop;
        }
        this.w = w;
    }
    
    public int pickIndex() {
        int result = index;
        ++value;
        if(w[result]==value)
        {
            index++;
            value = 0;
            if(index==w.length)
            {
                index = 0;
            }
        }
        return result;
    }
}
 */
 class Solution {
   
   private int[] nums;
   private int total;
   private Random rand;

   public Solution(int[] w) {
       this.rand = new Random();
       
       for (int i = 1; i < w.length; i++) {
           w[i] += w[i - 1];
       }
       
       this.nums = w;
       this.total = w[w.length - 1];
   }
   
   public int pickIndex() {
       // no numbers to pick!
       if (this.total == 0)
           return -1;
       
       int n = this.rand.nextInt(this.total) + 1; // the value pulled for {2, 5, 7} could be 0, 1, 2 all the way up to 7; we want a pulled value of 2 to actually coordinate with the second index (5), because three numbers do not fall into the range!
        
       //I actually used random.nextInt( wSums[len-1] + 1), and I know why it failed. For wsum[] = {2,7,10,14}, it generates a random value in range [0, 14], totally 15 numbers. If the random number is 0, 1, 2, our code will return index 0, so the probability for selecting the first one is 3/15.
       
       // this is the implementation of a left searching binary search
       int lo = 0, hi = this.nums.length - 1;
       while (lo < hi) {
           int mid = lo + (hi - lo) / 2;
           
           // pulled the exact value of an index
           if (this.nums[mid] == n)
               return mid;
           else if (this.nums[mid] < n)
               lo = mid + 1;
           else
               hi = mid; // find the leftmost value incase two of the indexes are the same and one is zero
       }
       return lo;
   }
}

/**
* Your Solution object will be instantiated and called as such:
* Solution obj = new Solution(w);
* int param_1 = obj.pickIndex();
*/