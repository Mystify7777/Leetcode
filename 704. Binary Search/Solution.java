class Solution {
    /**
     * Searches for a target value in a sorted array using Binary Search.
     *
     * @param nums The sorted array of integers.
     * @param target The integer to search for.
     * @return The index of the target if found, otherwise -1.
     */
    public int search(int[] nums, int target) {
        // Initialize pointers for the start and end of the array.
        int low = 0;
        int high = nums.length - 1;

        // Loop until the two pointers cross.
        while (low <= high) {
            // Calculate the middle index to avoid potential overflow.
            int mid = low + (high - low) / 2;

            // Case 1: The middle element is the target.
            if (nums[mid] == target) {
                return mid;
            }
            // Case 2: The target is smaller, so it must be in the left half.
            else if (nums[mid] > target) {
                high = mid - 1;
            }
            // Case 3: The target is larger, so it must be in the right half.
            else {
                low = mid + 1;
            }
        }

        // If the loop finishes, the target was not found.
        return -1;
    }
}