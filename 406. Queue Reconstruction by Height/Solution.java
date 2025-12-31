// 406. Queue Reconstruction by Height
class Solution {
    public int[][] reconstructQueue(int[][] people) {
	Arrays.sort(people, (a,b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);

	List<int[]> ordered = new LinkedList<>();
	for (int[] p: people) ordered.add(p[1], p);

	return ordered.toArray(new int[people.length][2]);
}
}

//why is queue implementation faster?
/**
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] == b[0])? a[1] - b[1] : b[0] - a[0]);
        for(int i = 0; i < people.length; i++) pq.offer(people[i]);

        List<int[]> list = new ArrayList<>(people.length);
        while(!pq.isEmpty()) {
            int[] current = pq.poll();
            list.add(current[1], current);
        }
        return list.toArray(people);
    }
}
 */