
// 621. Task Scheduler
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        Arrays.sort(freq);
        int chunk = freq[25] - 1;
        int idle = chunk * n;

        for (int i = 24; i >= 0; i--) {
            idle -= Math.min(chunk, freq[i]);
        }

        return idle < 0 ? tasks.length : tasks.length + idle;
    }
}
/**
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] frequencies = new int[26];
        for (char letter : tasks) {
            frequencies[letter - 'A']++;
        }
        int maxFreq = 0;
        for (int freq : frequencies) {
            if (freq > maxFreq) {
                maxFreq = freq;
            }
        }
        int maxCount = 0;
        for (int frq : frequencies) {
            if (frq == maxFreq) {
                maxCount++;
            }
        }
        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + maxCount);
    }
} */