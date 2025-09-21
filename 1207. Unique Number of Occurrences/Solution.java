// 1207. Unique Number of Occurrences
/*
import java.util.Arrays;

class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        Arrays.sort(arr);
        int[] v = new int[arr.length];
        int idx = 0;

        for (int i = 0; i < arr.length; i++) {
            int cnt = 1;

            // Count occurrences of the current element
            while (i + 1 < arr.length && arr[i] == arr[i + 1]) {
                cnt++;
                i++;
            }

            v[idx++] = cnt;
        }

        Arrays.sort(v);

        for (int i = 1; i < v.length; i++) {
            if (v[i] == v[i - 1]) {
                return false;
            }
        }

        return true;
    }
}
*/

/*
class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : arr) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }

        Set<Integer> s = new HashSet<>();
        for (int x : freq.values()) {
            s.add(x);
        }

        return freq.size() == s.size();
    }
}
*/

class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        // Step 1: Find the min and max value in the array
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }

        // Step 2: Count the occurrences for each value using a frequency array
        int range = max - min + 1;
        int[] freq = new int[range];
        for (int num : arr) {
            freq[num - min]++;
        }

        // Step 3: Use a boolean array to track which occurrence counts have been seen
        boolean[] seen = new boolean[arr.length + 1]; // +1 because occurrence can be up to arr.length
        for (int count : freq) {
            if (count > 0) {
                if (seen[count]) {
                    // If this occurrence count already exists, return false
                    return false;
                }
                seen[count] = true;
            }
        }
        return true;
    }
}
 
