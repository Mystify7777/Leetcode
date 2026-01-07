// 1122. Relative Sort Array
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] cnt = new int[1001];
        for(int n : arr1) cnt[n]++;
        int i = 0;
        for(int n : arr2) {
            while(cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        for(int n = 0; n < cnt.length; n++) {
            while(cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }
}

class Solution2 {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int n : arr1) map.put(n, map.getOrDefault(n, 0) + 1);
        int i = 0;
        for(int n : arr2) {
            for(int j = 0; j < map.get(n); j++) {
                arr1[i++] = n;
            }
            map.remove(n);
        }
        for(int n : map.keySet()){
            for(int j = 0; j < map.get(n); j++) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }
}

class Solution1 {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int n : arr1) {
            if (map.containsKey(n)) {
                map.put(n, map.getOrDefault(n, 0) + 1);
            } else {
                pq.add(n);
            }
        }
        int i = 0;
        for (int j = 0; j < arr2.length; j++) {
            if (map.containsKey(arr2[j])) {
                for (int k = map.get(arr2[j]); k > 0; k--) {
                    arr1[i++] = arr2[j];
                }
            }
        }
        while(!pq.isEmpty()) {
            arr1[i++] = pq.poll();
        }
        return arr1;
    }
}