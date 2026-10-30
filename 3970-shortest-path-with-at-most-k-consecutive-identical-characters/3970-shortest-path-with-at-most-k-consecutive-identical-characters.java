import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[] { edge[1], edge[2] });
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[][] dist = new int[n][k + 1];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        pq.offer(new int[] { 0, 0, 1 });
        dist[0][1] = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0];
            int u = curr[1];
            int count = curr[2];

            if (u == n - 1) {
                return d;
            }

            if (d > dist[u][count]) {
                continue;
            }

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int w = edge[1];
                
                int nextCount = 1;
                if (labels.charAt(u) == labels.charAt(v)) {
                    nextCount = count + 1;
                }

                if (nextCount > k) {
                    continue;
                }

                if (d + w < dist[v][nextCount]) {
                    dist[v][nextCount] = d + w;
                    pq.offer(new int[] { dist[v][nextCount], v, nextCount });
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int count = 1; count <= k; count++) {
            ans = Math.min(ans, dist[n - 1][count]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}