// 2069. Walking Robot Simulation II
// Added using AI
// https://leetcode.com/problems/walking-robot-simulation-ii/
class Robot2 {
    int x, y, width, height;
    String dir;

    public Robot2(int width, int height) {
        this.x = 0; this.y = 0;
        this.dir = "East" ;
        this.width = width; this.height = height;
    }

    public void step(int num) {
        int perim = 2 * (width - 1) + 2 * (height - 1);
        num %= perim;
        if (num == 0) num = perim;

        while (num > 0) {
            int nx = x, ny = y;
            if (dir.equals("East")) {
                int maxX = Math.min(x + num, width - 1);
                int rem  = num - (maxX - x) ;
                num = rem;
                if (rem == 0) x = maxX;
                else          { x = maxX; dir = "North"; }
            } else if (dir.equals("West")) {
                int minX = Math.max(x - num, 0);
                int rem  = num - (x - minX) ;
                num = rem;
                if (rem == 0) x = minX;
                else          { x = minX; dir = "South"; }
            } else if (dir.equals("North")) {
                int maxY = Math.min(y + num, height - 1);
                int rem  = num - (maxY - y) ;
                num = rem;
                if (rem == 0) y = maxY;
                else          { y = maxY; dir = "West"; }
            } else if (dir.equals("South")) {
                int minY = Math.max(y - num, 0);
                int rem  = num - (y - minY) ;
                num = rem;
                if (rem == 0) y = minY;
                else          { y = minY; dir = "East"; }
            }
        }
    }

    public int[] getPos() { return new int[]{x, y}; }
    public String getDir() { return dir; }
}

class Robot {

    int direction;
    String[] directionName;
    int width, height;
    int[] position;
    int[][] stepMovement;
    int num;

    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new int[2];
        this.position[0] = 0;
        this.position[1] = 0;
        this.direction = 0;
        this.directionName = new String[]{"East", "North", "West", "South"};
        this.stepMovement = new int[][]{{1,0},  {0,1}, {-1, 0}, {0, -1}};
        this.num = 0;

    }
    
    private void lazyStep(int num){
        num = num%(2*(this.width + this.height) - 4);
        if(num == 0){
            num = 2*(this.width + this.height) - 4;
        }
        while(num > 0){
            switch(direction){
                case 0:
                    if(this.position[0] + num > this.width - 1){
                        num = num - (this.width - this.position[0] - 1);
                        this.direction = (this.direction + 1)%4;
                        this.position[0] = this.width-1;
                    }
                    else{
                        this.position[0] = this.position[0] + num;
                        num = 0;
                    }
                    break;
                case 2:
                    if(this.position[0] - num < 0){
                        num = num - (this.position[0]);
                        this.direction = (this.direction + 1)%4;
                        this.position[0] = 0;
                    }
                    else{
                        this.position[0] = this.position[0] - num;
                        num = 0;
                    }
                    break;
                case 1:
                    if(this.position[1] + num > this.height - 1){
                        num = num - (this.height - this.position[1] - 1);
                        this.direction = (this.direction + 1)%4;
                        this.position[1] = this.height - 1;
                    }
                    else{
                        this.position[1] = this.position[1] + num;
                        num = 0;
                    }
                    break;
                case 3:
                    if(this.position[1] - num < 0){
                        num = num - (this.position[1]);
                        this.direction = (this.direction + 1)%4;
                        this.position[1] = 0;
                    }
                    else{
                        this.position[1] = this.position[1] - num;
                        num = 0;
                    }
                    break;
            }
        }
        this.num = 0;
    }
    public void step(int num) {
        this.num += num;
    }
    
    public int[] getPos() {
        if(this.num > 0)
            this.lazyStep(this.num);
        return this.position;
    }
    
    public String getDir() {
        if(this.num > 0)
            this.lazyStep(this.num);
        return this.directionName[this.direction];
    }
}

/**
 * Your Robot object will be instantiated and called as such:
 * Robot obj = new Robot(width, height);
 * obj.step(num);
 * int[] param_2 = obj.getPos();
 * String param_3 = obj.getDir();
 */