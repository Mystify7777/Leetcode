// 3454. Separate Squares II
// https://leetcode.com/problems/separate-squares-ii/
class Solution {
    // Helper class to represent active X-intervals
    private static class Interval implements Comparable<Interval> {
        int start, end;
        
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        // Needed for sort
        public int compareTo(Interval other) {
            if (this.start != other.start) return Integer.compare(this.start, other.start);
            return Integer.compare(this.end, other.end);
        }

        // Needed for removing specific objects from ArrayList
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return start == interval.start && end == interval.end;
        }
    }

    // Helper class for Sweep Line events
    private static class Event implements Comparable<Event> {
        int y;
        int type; // 1 for start, -1 for end
        int xStart, xEnd;

        Event(int y, int type, int xStart, int xEnd) {
            this.y = y;
            this.type = type;
            this.xStart = xStart;
            this.xEnd = xEnd;
        }

        public int compareTo(Event other) {
            return Integer.compare(this.y, other.y);
        }
    }

    public double separateSquares(int[][] squares) {
        List<Event> sweepEvents = new ArrayList<>();
        for (int[] sq : squares) {
            int x = sq[0];
            int y = sq[1];
            int l = sq[2];
            sweepEvents.add(new Event(y, 1, x, x + l));
            sweepEvents.add(new Event(y + l, -1, x, x + l));
        }

        Collections.sort(sweepEvents);

        List<Interval> activeIntervals = new ArrayList<>();
        // Store strips as: [y_bottom, height, union_width]
        List<double[]> processedStrips = new ArrayList<>();
        
        double totalArea = 0;
        int prevY = sweepEvents.get(0).y;

        for (Event event : sweepEvents) {
            // Process the gap (strip) between the previous event and this one
            if (event.y > prevY) {
                double unionWidth = getUnionWidth(activeIntervals);
                double height = (double) event.y - prevY;
                
                if (unionWidth > 0) {
                    processedStrips.add(new double[]{prevY, height, unionWidth});
                    totalArea += height * unionWidth;
                }
            }

            // Update active intervals list
            Interval currentInterval = new Interval(event.xStart, event.xEnd);
            if (event.type == 1) {
                activeIntervals.add(currentInterval);
            } else {
                activeIntervals.remove(currentInterval);
            }
            
            prevY = event.y;
        }

        // Second Pass: Find the split point
        double targetArea = totalArea / 2.0;
        double accumulatedArea = 0;

        for (double[] strip : processedStrips) {
            double bottomY = strip[0];
            double height = strip[1];
            double width = strip[2];
            double stripArea = height * width;

            if (accumulatedArea + stripArea >= targetArea) {
                double missingArea = targetArea - accumulatedArea;
                return bottomY + (missingArea / width);
            }
            accumulatedArea += stripArea;
        }

        return 0.0;
    }

    // Brute force union width calculation: O(K log K) where K is active squares
    private double getUnionWidth(List<Interval> intervals) {
        if (intervals.isEmpty()) return 0;

        // Create a copy to sort, so we don't mess up the main list order unnecessarily
        List<Interval> sorted = new ArrayList<>(intervals);
        Collections.sort(sorted);

        double unionLength = 0;
        double currentEnd = -1e18; // Negative infinity

        for (Interval iv : sorted) {
            if (iv.start >= currentEnd) {
                // Disjoint interval
                unionLength += (iv.end - iv.start);
                currentEnd = iv.end;
            } else if (iv.end > currentEnd) {
                // Overlapping interval
                unionLength += (iv.end - currentEnd);
                currentEnd = iv.end;
            }
        }
        return unionLength;
    }
}


class Solution2 {
    static final class Event {
        final long y;
        final int l, r;
        final int delta;
        Event(long y, int l, int r, int delta) { this.y = y; this.l = l; this.r = r; this.delta = delta; }
    }

    static final class SegTree {
        final long[] xs;
        final long[] cover;
        final int[] cnt;
        SegTree(long[] xs) {
            this.xs = xs;
            int n = Math.max(1, xs.length - 1);
            this.cover = new long[n << 2];
            this.cnt = new int[n << 2];
        }
        long covered() { return cover[1]; }
        void update(int l, int r, int delta) {
            if (l >= r) return;
            update(1, 0, xs.length - 1, l, r, delta);
        }
        private void update(int node, int L, int R, int ql, int qr, int delta) {
            if (qr <= L || R <= ql) return;
            if (ql <= L && R <= qr) {
                cnt[node] += delta;
                pushUp(node, L, R);
                return;
            }
            int mid = (L + R) >>> 1;
            update(node << 1, L, mid, ql, qr, delta);
            update(node << 1 | 1, mid, R, ql, qr, delta);
            pushUp(node, L, R);
        }
        private void pushUp(int node, int L, int R) {
            if (cnt[node] > 0) {
                cover[node] = xs[R] - xs[L];
            } else if (L + 1 == R) {
                cover[node] = 0;
            } else {
                cover[node] = cover[node << 1] + cover[node << 1 | 1];
            }
        }
    }

    public double separateSquares(int[][] squares) {
        int n = squares.length;
        if (n == 0) return -1;

        long[] xs = new long[2 * n];
        int p = 0;
        for (int[] s : squares) {
            long x1 = s[0];
            long x2 = (long) s[0] + s[2];
            xs[p++] = x1;
            xs[p++] = x2;
        }
        Arrays.sort(xs);
        int m = 1;
        for (int i = 1; i < xs.length; i++) {
            if (xs[i] != xs[m - 1]) xs[m++] = xs[i];
        }
        xs = Arrays.copyOf(xs, m);
        if (xs.length < 2) {
            long minY = Long.MAX_VALUE;
            for (int[] s : squares) minY = Math.min(minY, (long) s[1]);
            return (double) minY;
        }

        Event[] events = new Event[2 * n];
        int e = 0;
        for (int[] s : squares) {
            long x1 = s[0];
            long x2 = (long) s[0] + s[2];
            long y1 = s[1];
            long y2 = (long) s[1] + s[2];
            int l = lowerBound(xs, x1);
            int r = lowerBound(xs, x2);
            if (l < r) {
                events[e++] = new Event(y1, l, r, +1);
                events[e++] = new Event(y2, l, r, -1);
            }
        }
        if (e == 0) return -1;
        events = Arrays.copyOf(events, e);
        Arrays.sort(events, (a, b) -> Long.compare(a.y, b.y));

        SegTree st = new SegTree(xs);

        long[] sY = new long[e];
        long[] eY = new long[e];
        long[] base = new long[e];
        int gi = 0;

        long area = 0;
        long prevY = events[0].y;
        long baseLen = 0;
        int i = 0;
        while (i < e) {
            long currY = events[i].y;
            long dy = currY - prevY;
            if (dy != 0 && baseLen != 0) {
                area += baseLen * dy;
                sY[gi] = prevY;
                eY[gi] = currY;
                base[gi] = baseLen;
                gi++;
            }
            int j = i;
            while (j < e && events[j].y == currY) {
                st.update(events[j].l, events[j].r, events[j].delta);
                j++;
            }
            baseLen = st.covered();
            prevY = currY;
            i = j;
        }

        if (area == 0) return prevY;
        double target = area / 2.0;
        long pref = 0;
        for (int k = 0; k < gi; k++) {
            long a = base[k] * (eY[k] - sY[k]);
            if (pref + a < target) {
                pref += a;
            } else {
                double remain = target - pref;
                return sY[k] + remain / base[k];
            }
        }
        return prevY;
    }

    private static int lowerBound(long[] a, long key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}