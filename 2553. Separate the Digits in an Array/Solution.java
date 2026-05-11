// 2553. Separate the Digits in an Array
// https://leetcode.com/problems/separate-the-digits-in-an-array/
import java.util.*;

public class Solution {
    public int[] separateDigits(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for (int num : nums) {
            String s = String.valueOf(num);
            for (char ch : s.toCharArray()) {
                list.add(ch - '0');
            }
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}

class Solution2 {
    public int[] separateDigits(int[] arr) {
        int totalDigits = 0;

        for (int n : arr) {
            int temp = n;

            if (temp == 0) {
                totalDigits++;
            } else {
                while (temp > 0) {
                    totalDigits++;
                    temp /= 10;
                }
            }
        }

        int[] ans = new int[totalDigits];
        int index = totalDigits - 1;

        for (int i = arr.length - 1; i >= 0; i--) {
            int num = arr[i];

            if (num == 0) {
                ans[index--] = 0;
            } else {
                while (num > 0) {
                    ans[index--] = num % 10;
                    num /= 10;
                }
            }
        }

        return ans;
    }
}
