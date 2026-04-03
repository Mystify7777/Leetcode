// 3661. Maximum Walls Destroyed by Robots
// https://leetcode.com/problems/maximum-walls-destroyed-by-robots/
class Solution {

    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] num = new int[n];
        Map<Integer, Integer> robotsToDistance = new HashMap<>();

        for (int i = 0; i < n; i++) {
            robotsToDistance.put(robots[i], distance[i]);
        }

        Arrays.sort(robots);
        Arrays.sort(walls);

        for (int i = 0; i < n; i++) {
            int pos1 = upperBound(walls, robots[i]);

            int leftPos = 0;
            if (i >= 1) {
                int leftBound = Math.max(
                    robots[i] - robotsToDistance.get(robots[i]),
                    robots[i - 1] + 1
                );
                leftPos = lowerBound(walls, leftBound);
            } else {
                leftPos = lowerBound(
                    walls,
                    robots[i] - robotsToDistance.get(robots[i])
                );
            }
            left[i] = pos1 - leftPos;

            int rightPos = 0;
            if (i < n - 1) {
                int rightBound = Math.min(
                    robots[i] + robotsToDistance.get(robots[i]),
                    robots[i + 1] - 1
                );
                rightPos = upperBound(walls, rightBound);
            } else {
                rightPos = upperBound(
                    walls,
                    robots[i] + robotsToDistance.get(robots[i])
                );
            }
            int pos2 = lowerBound(walls, robots[i]);
            right[i] = rightPos - pos2;

            if (i == 0) {
                continue;
            }
            int pos3 = lowerBound(walls, robots[i - 1]);
            num[i] = pos1 - pos3;
        }

        int subLeft = left[0];
        int subRight = right[0];
        for (int i = 1; i < n; i++) {
            int currentLeft = Math.max(
                subLeft + left[i],
                subRight -
                right[i - 1] +
                Math.min(left[i] + right[i - 1], num[i])
            );
            int currentRight = Math.max(
                subLeft + right[i],
                subRight + right[i]
            );
            subLeft = currentLeft;
            subRight = currentRight;
        }

        return Math.max(subLeft, subRight);
    }

    private int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}


class Solution2 {

    public static final int INF = Integer.MAX_VALUE;
    public static final int NEG_INF = Integer.MIN_VALUE;

    record Robot(int position, int distance) {
    }

    /*
     * Let dpLeft[i], dpRight[i] = count of walls broken using 0 to i-th robot and i-th robot fires in left or right direction.
     * prepare leftWall[n], rightWall[n] to store the number of walls i-th robot can break in left or right direction.
     * prepare common[n], where common[i] = common walls destroyed by i & i-1 robots when they shoot at each other.
     *
     * Base case: dpLeft[0] = leftWall[0], dpRight[0] = rightWall[0], common[0] = 0.
     * Recurrence relation:
     * => dpLeft[i] = leftWall[i] + Max(i-1 th shoots left, i-1 th shoots right)
     *              = leftWall[i] + Max(dpLeft[i-1], dpRight[i-1] - common[i])
     *
     * => dpRight[i] = rightWall[i] + Max(i-1 th shoots left, i-1 th shoots right)
     *               = rightWall[i] + Max(dpLeft[i-1], dpRight[i-1])
     *
     * We have int[] robots, int[] distance, int[] walls.
     * Make Pair<robot -> distance> and sort it. Also sort walls.
     *
     * Now, how to prepare leftWall[n], rightWall[n], common[n] ------
     * => for leftWall, robot shooting range is [max(robot[i-1] + 1, robot[i] - distance[i]), robot[i]]
     *      start a window [left, right] and put move left right until its in robot shooting range.
     * => similarly for rightWall, where robot shooting range is [robot[i], min(robot[i+1] - 1, robot[i] + distance[i])]
     * => for common, we need to find the common walls broken by i & i-1 robots.
     *      common walls in i-1 th right window and i th left window ... ignore if this window is invalid, i.e., left > right.
     * */
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        Robot[] robotRecords = new Robot[n];

        for (int i = 0; i < n; i++) {
            robotRecords[i] = new Robot(robots[i], distance[i]);
        }

        Arrays.sort(robotRecords, Comparator.comparingInt(o -> o.position));
        Arrays.sort(walls);
        return maxWalls(robotRecords, n, walls);
    }

    private int maxWalls(Robot[] robots, int n, int[] walls) {
        int[] dpLeft = new int[n];
        int[] dpRight = new int[n];
        int[] leftWall = prepareLeftWall(robots, n, walls);
        int[] rightWall = prepareRightWall(robots, n, walls);
        int[] common = prepareCommon(robots, n, walls);

        dpLeft[0] = leftWall[0];
        dpRight[0] = rightWall[0];

        for (int i = 1; i < n; i++) {
            dpLeft[i] = leftWall[i] + Math.max(dpLeft[i - 1], dpRight[i - 1] - common[i]);
            dpRight[i] = rightWall[i] + Math.max(dpLeft[i - 1], dpRight[i - 1]);
        }

        return Math.max(dpLeft[n - 1], dpRight[n - 1]);
    }

    private int[] prepareLeftWall(Robot[] robots, int n, int[] walls) {
        int[] leftWall = new int[n];
        int wallSize = walls.length;
        int left = 0;
        int right = -1;

        for (int i = 0; i < n; i++) {
            Robot robot = robots[i];
            int prevRobotPosition = (i == 0) ? NEG_INF : robots[i - 1].position;
            int shootStart = Math.max(prevRobotPosition + 1, robot.position - robot.distance);
            int shootEnd = robot.position;

            while (right + 1 < wallSize && walls[right + 1] <= shootEnd) ++right;
            while (left < wallSize && walls[left] < shootStart) ++left;

            if (left <= right) leftWall[i] = right - left + 1;
        }
        return leftWall;
    }

    private int[] prepareRightWall(Robot[] robots, int n, int[] walls) {
        int[] rightWall = new int[n];
        int wallSize = walls.length;
        int left = 0;
        int right = -1;

        for (int i = 0; i < n; i++) {
            Robot robot = robots[i];
            int nextRobotPosition = (i == n - 1) ? INF : robots[i + 1].position;
            int shootStart = robot.position;
            int shootEnd = Math.min(nextRobotPosition - 1, robot.position + robot.distance);

            while (right + 1 < wallSize && walls[right + 1] <= shootEnd) ++right;
            while (left < wallSize && walls[left] < shootStart) ++left;

            if (left <= right) rightWall[i] = right - left + 1;
        }
        return rightWall;
    }


    private int[] prepareCommon(Robot[] robots, int n, int[] walls) {
        int[] common = new int[n];
        int wallSize = walls.length;
        int left = 0;
        int right = -1;

        for (int i = 1; i < n; i++) {
            Robot prev = robots[i - 1];
            Robot curr = robots[i];
            int shootStart = Math.max(prev.position + 1, curr.position - curr.distance);
            int shootEnd = Math.min(curr.position - 1, prev.position + prev.distance);

            if (shootStart > shootEnd) continue;

            while (right + 1 < wallSize && walls[right + 1] <= shootEnd) ++right;
            while (left < wallSize && walls[left] < shootStart) ++left;

            if (left <= right) common[i] = right - left + 1;
        }
        return common;
    }
}