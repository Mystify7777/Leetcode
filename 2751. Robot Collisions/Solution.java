// 2751. Robot Collisions
// https://leetcode.com/problems/robot-collisions/
class Solution {
    public List<Integer> survivedRobotsHealths(int[] pos, int[] h, String d) {

        int n = pos.length;
        Integer[] order = new Integer[n];
        for(int i=0;i<n;i++) order[i]=i;

        Arrays.sort(order,(a,b)->pos[a]-pos[b]);

        boolean[] alive = new boolean[n];
        Arrays.fill(alive,true);

        Deque<Integer> st = new ArrayDeque<>();

        for(int idx:order){

            if(d.charAt(idx)=='R') st.push(idx);

            else{
                while(!st.isEmpty()){

                    int top = st.peek();

                    if(h[top] < h[idx]){
                        alive[top]=false;
                        st.pop();
                        h[idx]--;
                    }
                    else if(h[top] > h[idx]){
                        alive[idx]=false;
                        h[top]--;
                        break;
                    }
                    else{
                        alive[top]=false;
                        alive[idx]=false;
                        st.pop();
                        break;
                    }
                }
            }
        }

        List<Integer> res=new ArrayList<>();
        for(int i=0;i<n;i++)
            if(alive[i]) res.add(h[i]);

        return res;
    }
}

class Solution2 {
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++)
            indices[i] = i;

        Arrays.sort(indices, (a, b) -> positions[a] - positions[b]);

        int[] st = new int[n];
        int top = -1;

        for (int idx : indices) {
            if (directions.charAt(idx) == 'R') {
                st[++top] = idx; // push right-moving robot
            } else {
                while (top >= 0 && healths[idx] > 0) {
                    int rightRobot = st[top]; // peek top
                    if (healths[rightRobot] < healths[idx]) {
                        healths[rightRobot] = 0;
                        healths[idx]--;
                        top--; // pop
                    } else if (healths[rightRobot] > healths[idx]) {
                        healths[idx] = 0;
                        healths[rightRobot]--;
                    } else {
                        healths[rightRobot] = 0;
                        healths[idx] = 0;
                        top--; // pop
                    }
                }
            }
        }

        // Collect survivors from healths array
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (healths[i] > 0)
                result.add(healths[i]);
        }
        return result;
    }
}