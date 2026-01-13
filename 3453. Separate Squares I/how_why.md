Problem
- Find the minimum y-coordinate of a horizontal line that splits the total area of given axis-aligned squares into equal top and bottom parts (squares can overlap).

Key idea
- The area above the line is monotonic: as the line moves up, top area decreases and bottom area increases. That monotonicity lets us binary-search the answer.

Algorithm
1) Binary search on y between a loose lower bound (0) and upper bound (2e9). Iterate ~60 times to get double precision.
2) For a candidate line, iterate all squares and compute how much of each square lies above vs. below the line:
	- If the line is entirely below the square: all area counts as above.
	- If entirely above: all area counts as below.
	- If it cuts the square: split by the intersection height and add partial areas to above/below.
3) If above area > below area, move the search window up (line too low); else move it down. Return the upper bound after iterations.

Correctness sketch
- For any y, define f(y) = area_above(y) - area_below(y). As y increases, area_above is non-increasing and area_below is non-decreasing, so f(y) is monotonically non-increasing. The target line occurs where f(y)=0. Binary search on monotone f(y) converges to the smallest y with f(y)≤0, which is the minimal line balancing areas.

Complexity
- Each helper evaluation scans all squares: O(n). Binary search does O(log(precision)) iterations (~60), so O(n·60) time and O(1) extra space.
