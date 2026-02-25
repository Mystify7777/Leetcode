// 1356. Sort Integers by The Number of 1 Bits
// https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/
  
class Solution {
    public int[] sortByBits(int[] arr) {
        // Convert the input array to an array of Integer objects for sorting
        Integer[] nums = Arrays.stream(arr).boxed().toArray(Integer[]::new);

        // Create a custom comparator for sorting based on bit counts and numerical values
        Comparator<Integer> comparator = new BitCountComparator();

        // Sort the array using the custom comparator
        Arrays.sort(nums, comparator);

        // Convert the sorted Integer array back to a primitive int array
        return Arrays.stream(nums).mapToInt(Integer::intValue).toArray();
    }
}

class BitCountComparator implements Comparator<Integer> {
    private int findBitCount(int num) {
        // Count the number of set bits (1s) in the binary representation of num
        int count  = 0;

        while (num > 0) {
            count ++;
            num &= (num - 1);
        }

        return count ;
    }

    @Override
    public int compare(Integer a, Integer b) {
        int bitCountA = findBitCount(a);
        int bitCountB = findBitCount(b);

        if (bitCountA == bitCountB) {
            return a - b; // If bit counts are the same, compare numerically.
        }

        return bitCountA - bitCountB; // Sort by the bit count in ascending order.
    }
}

class Solution2 {
    public int[] sortByBits(int[] arr) {
        int n = arr.length;
        for(int i = 0; i < n; i++) arr[i] += 10001 * Integer.bitCount(arr[i]);
        quicksort(arr, 0, n - 1);
        for(int i = 0; i < n; i++) arr[i] %= 10001;
        return arr;
    }
    private static void quicksort(int[] nums, int left, int right) {
        if(left < right) {
            int part = partition(nums, left - 1, right + 1);
            quicksort(nums, left, part);
            quicksort(nums, part + 1, right);
        }
    }
    private static int partition(int[] nums, int left, int right) {
        int current = getPivot(nums[left + 1], nums[left + right >>> 1], nums[right - 1]), temp = 0;
        while(true) {
            do {
                left++;
            }while(nums[left] < current);
            do {
                right--;
            }while(nums[right] > current);
            
            if(left >= right) return right;

            temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }
    private static int getPivot(int a, int b, int c) {
        if((a >= b) ^ (a >= c)) return a;
        if((a >= b) ^ (c >= b)) return b;
        return c;
    }
}
