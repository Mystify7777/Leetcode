// 2833. Furthest Point From Origin
// https://leetcode.com/problems/furthest-point-from-origin/
class Solution {
    public int furthestDistanceFromOrigin(String moves) {
        int dist = 0, blanks = 0;

        for (char c : moves.toCharArray()) {
            if (c == 'L') dist--;
            else if (c == 'R') dist++;
            else blanks++;
        }

        return Math.abs(dist) + blanks;
    }
}