/** 
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return 	     -1 if num is higher than the picked number
 *			      1 if num is lower than the picked number
 *               otherwise return 0
 * int guess(int num);
 */
import java.util.Random;
class GuessGame {
    int guess(int num) {
        int  rand = new Random().nextInt(10);

        return num==rand?0:(num<rand?1:-1);
    }
}

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int first = 1, last = n;
        while (first <= last) {
            int mid = first + (last - first) / 2;
            int res = guess(mid);
            if (res == 0) {
                return mid;
            } else if (res == -1) {
                last = mid - 1;
            } else {
                first = mid + 1;
            }
        }
        return -1;
    }
}