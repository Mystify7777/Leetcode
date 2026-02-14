// 799. Champagne Tower
// https://leetcode.com/problems/champagne-tower
class Solution {
    public double champagneTower(int poured, int queryRow, int queryGlass) {
	if (poured == 0)
		return 0;
	
	var prevRow = new ArrayList<>(List.of((double) poured));

	while (queryRow-- > 0) {
		var champagneInEnds = Math.max(0, (prevRow.get(0) - 1) / 2);  // min champagne can be 0
		var currentRow = new ArrayList<>(List.of(champagneInEnds)); // list with first glass

		for (var i = 1; i < prevRow.size(); i++)
			currentRow.add(Math.max(0, (prevRow.get(i - 1) - 1) / 2) + // flow from top-left glass
						   Math.max(0, (prevRow.get(i) - 1) / 2));     // flow from top-right glass

		currentRow.add(champagneInEnds); // last glass
		prevRow = currentRow;
	}
	
	return Math.min(1, prevRow.get(queryGlass)); // max champagne can be 1
}
}

class Solution2 {
    public double champagneTower(int poured, int row, int col) {
        double[] dp = new double[col + 2];
        dp[0] = poured;
        for(int i = 0; i < row; i++) {
            for(int j = Math.min(i, col); j >= 0; j--) {
                if(dp[j] > 1) {
                    double val = (dp[j] - 1) / 2.0;
                    dp[j] = val;
                    dp[j + 1] += val;
                }else dp[j] = 0;
            }
        }
        return Math.min(1, dp[col]);
    }
}