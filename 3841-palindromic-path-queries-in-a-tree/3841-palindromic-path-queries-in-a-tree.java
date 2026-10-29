import java.util.*;

class Solution {
    private int[] head, to, next;
    private int edgeCount;
    private int[] parent, depth, heavy, headChain, pos;
    private int curPos;
    private int[] bit;
    private int N;

    private void addEdge(int u, int v) {
        to[edgeCount] = v;
        next[edgeCount] = head[u];
        head[u] = edgeCount++;
    }

    private int dfs(int u, int p, int d) {
        int size = 1;
        int maxCSize = 0;
        parent[u] = p;
        depth[u] = d;
        heavy[u] = -1;

        for (int e = head[u]; e != -1; e = next[e]) {
            int v = to[e];
            if (v != p) {
                int cSize = dfs(v, u, d + 1);
                size += cSize;
                if (cSize > maxCSize) {
                    maxCSize = cSize;
                    heavy[u] = v;
                }
            }
        }
        return size;
    }

    private void decompose(int u, int h) {
        headChain[u] = h;
        pos[u] = ++curPos;

        if (heavy[u] != -1) {
            decompose(heavy[u], h);
        }

        for (int e = head[u]; e != -1; e = next[e]) {
            int v = to[e];
            if (v != parent[u] && v != heavy[u]) {
                decompose(v, v);
            }
        }
    }

    private void bitUpdate(int idx, int val) {
        for (; idx <= N; idx += idx & -idx) {
            bit[idx] ^= val;
        }
    }

    private int bitQuery(int idx) {
        int sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum ^= bit[idx];
        }
        return sum;
    }

    private int queryRange(int l, int r) {
        return bitQuery(r) ^ bitQuery(l - 1);
    }

    private int queryPath(int u, int v) {
        int res = 0;
        while (headChain[u] != headChain[v]) {
            if (depth[headChain[u]] > depth[headChain[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }
            res ^= queryRange(pos[headChain[v]], pos[v]);
            v = parent[headChain[v]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        res ^= queryRange(pos[u], pos[v]);
        return res;
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.N = n;
        head = new int[n];
        Arrays.fill(head, -1);
        to = new int[2 * (n - 1)];
        next = new int[2 * (n - 1)];
        edgeCount = 0;

        for (int[] e : edges) {
            addEdge(e[0], e[1]);
            addEdge(e[1], e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        heavy = new int[n];
        headChain = new int[n];
        pos = new int[n];
        curPos = 0;

        dfs(0, 0, 0);
        decompose(0, 0);

        bit = new int[n + 1];
        char[] chars = s.toCharArray();
        for (int i = 0; i < n; i++) {
            bitUpdate(pos[i], 1 << (chars[i] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].charAt(0) == 'u') {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                if (chars[u] != c) {
                    int oldMask = 1 << (chars[u] - 'a');
                    int newMask = 1 << (c - 'a');
                    bitUpdate(pos[u], oldMask ^ newMask);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int mask = queryPath(u, v);
                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }
}