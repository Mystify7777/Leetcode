class Solution {
        public int[][] kClosest(int[][] points, int k) {
        Arrays.sort(points, Comparator.comparing(p -> p[0] * p[0] + p[1] * p[1]));
        return Arrays.copyOfRange(points, 0, k);
    }
}

//wtf is this mess of code..
/**
class Solution {
    private Random rand = new Random();

    public int[][] kClosest(int[][] points, int k) {
        int start = 0;
        int end = points.length - 1;
        while (start <= end) {
            int p = qselect(points, start, end);
            if (p == k - 1) {
                break;
            } else if (p > k - 1) {
                end = p - 1;
            } else {
                start = p + 1;
            }
        }
        int[][] result = new int[k][];
        for (int i = 0; i < k; ++i) {
            result[i] = points[i]; 
        }
        return result;
    }

    private int qselect(int[][] points, int start, int end) {
        int randomIdx = start + rand.nextInt(end - start + 1);
        swap(points, randomIdx, end);
        int pivotDist = distance(points[end]);
        int p = start;
        for (int i = start; i < end; ++i) {
            if (distance(points[i]) <= pivotDist) {
                swap(points, p, i);
                p++;
            }
        }
        swap(points, p, end);
        return p;
    }

    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    private int distance(int[] x) {
        return x[0]*x[0] + x[1]*x[1];
    }
}
 */