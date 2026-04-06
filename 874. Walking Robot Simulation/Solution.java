// 874. Walking Robot Simulation
// https://leetcode.com/problems/walking-robot-simulation/
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        // Store obstacles
        Set<String> blocked = new HashSet<>();
        for (int[] o : obstacles) {
            blocked.add(o[0] + "," + o[1]);
        }

        // Directions: North, East, South, West
        int[][] directions = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
        };

        int x = 0, y = 0;
        int dir = 0; // initially facing North
        int maxDist = 0;

        for (int cmd : commands) {
            if (cmd == -1) {
                dir = (dir + 1) % 4; // turn right
            } 
            else if (cmd == -2) {
                dir = (dir + 3) % 4; // turn left
            } 
            else {
                while (cmd-- > 0) {
                    int nx = x + directions[dir][0];
                    int ny = y + directions[dir][1];

                    // check obstacle
                    if (blocked.contains(nx + "," + ny)) break;

                    x = nx;
                    y = ny;

                    maxDist = Math.max(maxDist, x * x + y * y);
                }
            }
        }

        return maxDist;
    }
}
class Solution2 {
    static int[][] dirs = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    public int robotSim(int[] commands, int[][] obstacles) {
        int dirIdx = 0;
        int result = 0;

        Set<Long> set = new HashSet<>();

        for (int[] obs : obstacles ) {
            set.add( ((long)obs[0] << 32L) | (obs[1]&0x7fffffff) );
        }

        int xx = 0;
        int yy = 0;
        int[] dir = dirs[dirIdx];
        for ( int com : commands ) {
            if ( com == -1 ) { dirIdx = (dirIdx + 1) % 4; dir = dirs[dirIdx]; }
            else if ( com == -2 ) { dirIdx = (dirIdx + 3) % 4; dir = dirs[dirIdx]; }
            else {
                for ( int ii = 1; ii <= com; ii++ ) {
                    xx += dir[0];
                    yy += dir[1];
                    if ( set.contains( ((long)xx<<32L) | (yy&0x7fffffff) ) ) {
                        xx -= dir[0];
                        yy -= dir[1];
                        break;
                    }
                }
                result = Math.max(result, xx*xx + yy*yy);
            }
// System.out.printf("xx=%d, yy=%d, dir=%s\n", xx, yy, Arrays.toString(dir));
        }

        return result;
    }
}