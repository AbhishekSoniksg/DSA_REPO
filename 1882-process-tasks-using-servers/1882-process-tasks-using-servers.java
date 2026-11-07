
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[2] != b[2] ? a[2] - b[2] :
                      a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );

        for (int i = 0; i < servers.length; i++) {
            available.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[tasks.length];
        long time = 0;

        for (int i = 0; i < tasks.length; i++) {

            time = Math.max(time, i);

            while (!busy.isEmpty() && busy.peek()[2] <= time) {
                int[] s = busy.poll();
                available.offer(new int[]{s[0], s[1]});
            }

            if (available.isEmpty()) {
                time = busy.peek()[2];

                while (!busy.isEmpty() && busy.peek()[2] <= time) {
                    int[] s = busy.poll();
                    available.offer(new int[]{s[0], s[1]});
                }
            }

            int[] server = available.poll();

            ans[i] = server[1];

            busy.offer(new int[]{
                server[0],
                server[1],
                (int)(time + tasks[i])
            });
        }

        return ans;
    }
}
